package com.POC.AuthSecurity.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtilities jwtUtilities;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthFilter(JwtUtilities jwtUtilities, UserDetailsService userDetailsService) {
        this.jwtUtilities = jwtUtilities;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader= request.getHeader("Authorization");

        if(authHeader == null || authHeader=="")
        {
            filterChain.doFilter(request,response);
            return;
        }
        final String jwt = authHeader;
        final String userEmail = jwtUtilities.extractUsername(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtUtilities.isTokenValid(jwt,userDetails)){ // i don't understand why we use this condition
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request,response);
    }
}
