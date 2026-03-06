package com.dev.ECommerceProductService.dto;

import jakarta.validation.constraints.DecimalMin;
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

    @DecimalMin(value = "1.0")
    private Double price;

    @NotNull(message = "Category ID Cannot Be Null")
    @NotBlank(message = "Category ID Cannot Be Blank")
    private String category;

    @NotNull(message = "Brand Cannot Be Null")
    @NotBlank(message = "Brand Cannot Be Blank")
    private String brand;

    @NotNull(message = "Description Cannot Be Null")
    @NotBlank(message = "Description Cannot Be Blank")
    private String description;

    @NotNull(message = "Image Cannot Be Null")
    @NotBlank(message = "Image Cannot Be Blank")
    private String image;
}
