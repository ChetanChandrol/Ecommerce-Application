package com.dev.ecommerceuserservice.security;

import com.dev.ecommerceuserservice.models.Role;
import com.dev.ecommerceuserservice.models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private UUID id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public CustomUserDetails() {
    }


    public CustomUserDetails(User user) {

        this.id = user.getId();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.enabled = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
        this.accountNonExpired = true;
        authorities = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new CustomGrantedAuthority(role.getName()));
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
