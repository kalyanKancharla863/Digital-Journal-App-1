package com.digital_journal_app.backend.controller;

import com.digital_journal_app.backend.model.User;
import com.digital_journal_app.backend.repositories.UserRepository;
import com.digital_journal_app.backend.security.jwt.JwtUtils;
import com.digital_journal_app.backend.security.request.LoginRequest;
import com.digital_journal_app.backend.security.request.SignupRequest;
import com.digital_journal_app.backend.security.response.MessageResponse;
import com.digital_journal_app.backend.security.response.UserInfoResponse;
import com.digital_journal_app.backend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        originPatterns = "*",
        allowCredentials = "true"
)
public class AuthController {
    

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        }
        catch(AuthenticationException exception){
            Map<String,Object> map = new HashMap<>();
            map.put("message","Bad Credintials");
            map.put("status",false);
            return new ResponseEntity<Object>(
                    map, HttpStatus.NOT_FOUND
            );
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie cookie = jwtUtils.generateCookie(principal);
        UserInfoResponse loginResponse = new UserInfoResponse(principal.getId(), principal.getUsername());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(loginResponse);

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUserName(signupRequest.getUsername())){
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error: Username already taken")
            );
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error: Email already taken")
            );
        }
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
                );
        userRepository.save(user);

        return ResponseEntity.ok
                (new MessageResponse("User Registered Successfully"));
    }

    @GetMapping("/username")
    public String username(Authentication authentication){
        return authentication!=null?authentication.getName():null;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication){
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream()
                .map( auth -> auth.getAuthority())
                .collect(Collectors.toList());
        UserInfoResponse loginResponse = new UserInfoResponse(
                principal.getId(),
                principal.getUsername()
        );
        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = jwtUtils.cleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(new MessageResponse("You have been signout successfully"));
    }
}
