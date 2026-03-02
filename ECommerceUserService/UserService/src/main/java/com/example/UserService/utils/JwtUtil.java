package com.example.UserService.utils;

import com.example.UserService.exception.UserServiceException;
import com.example.UserService.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${token.expiry}")
    private Long tokenExpiry;

    @Autowired
    private PrivateKey privateKey;
    @Autowired
    private PublicKey publicKey;

    public String getToken(User loginUser) throws Exception {
        SignatureAlgorithm algorithm = Jwts.SIG.RS256;
        Instant createdAt = Instant.now();
        Instant expiryAt = createdAt.plus(tokenExpiry, ChronoUnit.DAYS);
        Map<String, Object> jsonForJWT = new HashMap<>();
        jsonForJWT.put("email", loginUser.getEmail());
        jsonForJWT.put("userId", loginUser.getId());
        jsonForJWT.put("role", loginUser.getRoles());

        return Jwts.builder()
                .issuedAt(Date.from(createdAt))
                .expiration(Date.from(expiryAt))
                .claims(jsonForJWT)
                .signWith(privateKey, algorithm)
                .compact();
    }

    public Claims parseToken(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(publicKey).build().
                    parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new UserServiceException("Jwt Token Has Expired", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new UserServiceException("Jwt Token Is Invalid", HttpStatus.BAD_REQUEST);
        }
    }

}
