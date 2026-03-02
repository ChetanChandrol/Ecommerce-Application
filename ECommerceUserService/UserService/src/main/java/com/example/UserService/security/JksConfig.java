package com.example.UserService.security;

import com.example.UserService.security.models.Authorization;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class JksConfig {
    @Value("${keyFile}")
    private String keyFile;
    @Value("${alias}")
    private String alias;
    @Value("${password}")
    private String password;
    @Value("${providerUrl}")
    private String providerUrl;

    private JWKSet buildJWKSet() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (InputStream fis = this.getClass().getClassLoader().getResourceAsStream(keyFile)) {
            keyStore.load(fis, alias.toCharArray());
            return JWKSet.load(keyStore, new PasswordLookup() {
                @Override
                public char[] lookupPassword(String s) {
                    return password.toCharArray();
                }
            });
        }
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        JWKSet jwkSet = buildJWKSet();
        return (jwkSelector, securityContext) ->
                jwkSelector.select(jwkSet);
    }
    // add a jws decoder

    @Bean
    AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().issuer(providerUrl).build();
    }

}
 