package com.dev.ECommerceProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String categoryName;
}
