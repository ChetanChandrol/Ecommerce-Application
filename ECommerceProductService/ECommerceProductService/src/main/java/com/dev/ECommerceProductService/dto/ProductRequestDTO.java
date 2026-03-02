package com.dev.ECommerceProductService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    @NotNull(message = "Title Cannot Be Null")
    @NotBlank(message = "Title Cannot Be Blank")
    private String title;
    @Min(1)
    private Double price;
    @NotNull(message = "CategoryID Cannot Be Null")
    @NotBlank(message = "CategoryID Cannot Be Blank")
    private String category;
    @NotNull(message = "Description Cannot Be Null")
    @NotBlank(message = "Description Cannot Be Blank")
    private String description;
    @NotNull(message = "Image Cannot Be Null")
    @NotBlank(message = "Image Cannot Be Blank")
    private String image;
}
