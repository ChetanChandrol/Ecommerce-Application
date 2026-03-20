package com.dev.ecommerceuserservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

@Configuration
public class JksConfiguration {

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
