package com.dev.ecommerceuserservice.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;


    public CustomGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public @Nullable String getAuthority() {
        return authority;
    }

    public CustomGrantedAuthority() {
    }
}
