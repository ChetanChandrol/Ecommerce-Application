package com.dev.orderservice.utils;

import com.dev.orderservice.security.LoggedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class SecurityUtils {
    public static LoggedUser getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof LoggedUser loggedUser) {
            return loggedUser;
        } else if (auth.getPrincipal() instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            Jwt token = jwtAuthenticationToken.getToken();
            return new LoggedUser(token.getClaim("userId"),
                    token.getClaim("email")
            );
        }
        return null;
    }
}
