package com.dev.ecommerceuserservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@SpringBootTest
class EcommerceUserServiceApplicationTests {
    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Test
    void contextLoads() {
    }

//    @Test
//    public void registeredClientRepository() {
//        RegisteredClient oidcClient = RegisteredClient.withId(java.util.UUID.randomUUID().toString())
//                .clientId("oidc-client")
//                .clientSecret("$2y$10$MoN/Yb6Ph6bZulAEhlAJIuqK4vXfyjnwjLaOVUScsgMheEROZ9HxS")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("https://oauth.pstmn.io/v1/callback")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//
//        registeredClientRepository.save(oidcClient);
//
//
//    }
}
