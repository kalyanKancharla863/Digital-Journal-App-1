package com.digital_journal_app.backend.security.jwt;

import com.digital_journal_app.backend.repositories.UserRepository;
import com.digital_journal_app.backend.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String jwt = jwtUtils.jwtFromCookies(request);
           if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
               String usernameFromToken = jwtUtils.getUsernameFromToken(jwt);
               UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(usernameFromToken);
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );

               SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
           }
       }
       catch (Exception e) {
           throw new RuntimeException(e);
       }
        filterChain.doFilter(request,response);
    }
}
