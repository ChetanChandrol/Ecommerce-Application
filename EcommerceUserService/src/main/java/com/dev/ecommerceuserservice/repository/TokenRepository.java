package com.dev.ecommerceuserservice.repository;

import com.dev.ecommerceuserservice.enums.TokenStatus;
import com.dev.ecommerceuserservice.models.Token;
import com.dev.ecommerceuserservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUserAndTokenStatus(User user, TokenStatus tokenStatus);


}
