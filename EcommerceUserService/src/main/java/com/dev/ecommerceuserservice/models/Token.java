package com.dev.ecommerceuserservice.models;

import com.dev.ecommerceuserservice.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token extends BaseModel {
    @Lob
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;
    @ManyToOne
    private User user;
}
