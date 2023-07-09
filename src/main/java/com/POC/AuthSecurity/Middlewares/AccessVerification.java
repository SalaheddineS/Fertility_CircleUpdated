package com.POC.AuthSecurity.Middlewares;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccessVerification {
    static public void verifyAccess(String email) {
        String emailFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = false;
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ADMIN")) {
                isAdmin = true;
                break;
            }
        }
        if (!emailFromToken.equals(email) && !isAdmin) {
            throw new RuntimeException("You are not allowed to update this user");
        }
    }
}
