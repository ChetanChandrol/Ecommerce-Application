package com.dev.ecommerceuserservice.security.OAuth2.repository;

import com.dev.ecommerceuserservice.security.OAuth2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}