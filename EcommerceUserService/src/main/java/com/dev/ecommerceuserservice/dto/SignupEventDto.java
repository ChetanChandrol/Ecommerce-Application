package com.dev.ecommerceuserservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupEventDto {
    private String to;
    private String from;
    private String body;
    private String subject;
}
