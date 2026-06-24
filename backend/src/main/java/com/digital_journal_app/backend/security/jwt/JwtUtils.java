package com.digital_journal_app.backend.security.jwt;

import com.digital_journal_app.backend.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtCookie}")
    private String jwtCookie;

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String jwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie==null)return null;
        return cookie.getValue();
    }

    public ResponseCookie cleanJwtCookie() {
        return ResponseCookie.from(jwtCookie,null)
                .path("/api")
                .build();
    }

    public ResponseCookie generateCookie(UserDetailsImpl principal) {
        String jwtToken = generateTokenFromUsername(principal);
        return ResponseCookie.from(jwtCookie,jwtToken)
                .path("/api")
                .maxAge(24*60*60)
                .httpOnly(false)
                .build();
    }
    public String generateTokenFromUsername(UserDetails userDetails ){
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
//                .expiration( new Date((new Date()).getTime() + jwtExpirationMs ))
                .signWith(key())
                .compact();
    }


    //Decrypt
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
    public boolean validateJwtToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(token);
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
