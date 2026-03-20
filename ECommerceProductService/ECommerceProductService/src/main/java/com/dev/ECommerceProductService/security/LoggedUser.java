package com.dev.ECommerceProductService.security;

import com.dev.ECommerceProductService.dto.ValidateTokenResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LoggedUser {
    private String email;
    private UUID id;
    private List<GrantedAuthority> grantedAuthorities;

    public LoggedUser(ValidateTokenResponseDto validateTokenResponseDto) {
        this.email = validateTokenResponseDto.getEmail();
        this.id = validateTokenResponseDto.getId();
        grantedAuthorities = new ArrayList<>();
        for (String role:validateTokenResponseDto.getRoles())
        {
            grantedAuthorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role;
                }
            });
        }
    }
}
