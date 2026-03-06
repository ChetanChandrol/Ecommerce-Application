package com.dev.ECommerceProductService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class FilterDTO {
    private String key;
    private List<String> values;
}
