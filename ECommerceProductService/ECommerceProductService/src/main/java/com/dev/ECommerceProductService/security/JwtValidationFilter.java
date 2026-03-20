package com.dev.ECommerceProductService.security;

import com.dev.ECommerceProductService.client.UserServiceClient;
import com.dev.ECommerceProductService.dto.ValidateTokenResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try {
           String token = request.getHeader("Authorization");

           if (token != null && token.startsWith("Bearer ")) {
               ValidateTokenResponseDto validateTokenResponse = userServiceClient.validateToken(token);
               if (validateTokenResponse.getTokenStatus().equals("ACTIVE")) {

                   LoggedUser user = new LoggedUser(validateTokenResponse);
                   UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(
                                   user,
                                   null,
                                   user.getGrantedAuthorities()

                           );

                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }
           }
       } catch (Exception e) {
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           return;
       }
        filterChain.doFilter(request, response);
    }
}
