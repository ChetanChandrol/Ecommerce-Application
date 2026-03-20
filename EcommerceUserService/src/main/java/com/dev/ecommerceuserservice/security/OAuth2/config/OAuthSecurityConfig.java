package com.dev.ecommerceuserservice.security.OAuth2.config;

import com.dev.ecommerceuserservice.security.CustomUserDetails;
import com.dev.ecommerceuserservice.security.JwtAuthenticationFilter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class OAuthSecurityConfig {
    @Value("${keyFile}")
    private String keyFile;
    @Value("${alias}")
    private String alias;
    @Value("${password}")
    private String password;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .oidc(Customizer.withDefaults())    // Enable OpenID Connect 1.0
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated()
                )
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/login", "/auth/signup").permitAll()
                        .requestMatchers("/role/**", "/user/role/{id}", "/user").hasAuthority("ADMIN")
                        .requestMatchers("/user/{id}").hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().authenticated()
                )

                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }*/

    /*@Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient oidcClient = RegisteredClient.withId(java.util.UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }*/

    /* @Bean
     public JWKSource<SecurityContext> jwkSource() {
         KeyPair keyPair = generateRsaKey();
         RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
         RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
         RSAKey rsaKey = new RSAKey.Builder(publicKey)
                 .privateKey(privateKey)
                 .keyID(UUID.randomUUID().toString())
                 .build();
         JWKSet jwkSet = new JWKSet(rsaKey);
         return new ImmutableJWKSet<>(jwkSet);
     }*/
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

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return (context) -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims((claims) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                            .stream()
                            .map(c -> "ROLE_" + c)
                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                    claims.put("roles", roles);

                    Object principal = context.getPrincipal().getPrincipal();
                    if (principal instanceof CustomUserDetails userDetails) {
                        claims.put("email", userDetails.getUsername());
                        claims.put("userId", userDetails.getId());
                    }

                });
            }
        };
    }


    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}