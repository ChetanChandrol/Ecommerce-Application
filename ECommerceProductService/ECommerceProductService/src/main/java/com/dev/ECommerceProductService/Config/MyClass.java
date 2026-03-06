//package com.dev.ECommerceProductService.Config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class MyClass {
//
//    public JwtAuthenticationConverter jwtAuthConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("roles");
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthConverter());
//        http.authorizeHttpRequests(
//                authorizeRequests -> authorizeRequests.anyRequest().permitAll()
//        );
//        return http.build();
//    }
//}
