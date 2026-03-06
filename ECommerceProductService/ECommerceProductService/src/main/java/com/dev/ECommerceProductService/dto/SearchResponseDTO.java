package com.dev.ECommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class SearchResponseDTO {
    Page<ProductResponseDTO> productsPage;
}
