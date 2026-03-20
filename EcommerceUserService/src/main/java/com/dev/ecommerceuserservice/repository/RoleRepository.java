package com.dev.ecommerceuserservice.repository;

import com.dev.ecommerceuserservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
