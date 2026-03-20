package com.dev.ecommerceuserservice.utils;

import com.dev.ecommerceuserservice.exception.UserServiceException;
import com.dev.ecommerceuserservice.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@Component
public class  JwtUtil {

    @Value("${jwt.token.expiry}")
    private Long tokenExpiry;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public JwtUtil(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getToken(User loginUser) {

            SignatureAlgorithm signatureAlgorithm = Jwts.SIG.RS256;

            Instant createdAt = Instant.now();
            Instant expiryAt = createdAt.plus(tokenExpiry, ChronoUnit.DAYS);

            HashMap<String, Object> claims = new HashMap<>();

            claims.put("id", loginUser.getId());
            claims.put("name", loginUser.getName());
            claims.put("email", loginUser.getEmail());
            claims.put("roles",
                    loginUser.getRoles()
                            .stream()
                            .map(role -> role.getName())
                            .toList()
            );

            return Jwts.builder()
                    .issuer("https://localhost:8081")
                    .setIssuedAt(Date.from(createdAt))
                    .setExpiration(Date.from(expiryAt))
                    .signWith(privateKey, signatureAlgorithm)
                    .claims(claims)
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
