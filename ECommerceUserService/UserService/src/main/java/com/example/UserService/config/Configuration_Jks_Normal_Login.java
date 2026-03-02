package com.example.UserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

@org.springframework.context.annotation.Configuration
public class Configuration_Jks_Normal_Login {

//    @Order(1)//to set priority among filters
//    @Bean
//    public SecurityFilterChain filteringCriteria(HttpSecurity http) throws Exception {
//
//
//        http.cors(cors -> cors.disable());
//
//       // http.authorizeHttpRequests(auth -> auth.requestMatchers("/order/**").permitAll());
//        // http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").authenticated());
//        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
//        return http.build();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PrivateKey loadPrivateKey() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream("jwtiscool.jks")) {

            keyStore.load(is, "jwtiscool".toCharArray());
        }

        Key key = keyStore.getKey("jwtiscool", "jwtiscool".toCharArray());

        if (key instanceof PrivateKey privateKey) {
            return privateKey;
        }

        throw new RuntimeException("Private key not found");
    }

    @Bean
    public PublicKey loadPublicKey() throws Exception {

        KeyStore keyStore = KeyStore.getInstance("JKS");

        try (InputStream is =
                     getClass().getClassLoader().getResourceAsStream("jwtiscool.jks")) {

            keyStore.load(is, "jwtiscool".toCharArray());
        }

        Certificate certificate = keyStore.getCertificate("jwtiscool");

        return certificate.getPublicKey();
    }
}
