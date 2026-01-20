package com.dev.ECommerceProductService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank (message = "Category Not Blank")
    @NotNull(message = "Category Not Null")
    private String categoryName;
}
