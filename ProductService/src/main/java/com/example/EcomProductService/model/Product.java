package com.example.EcomProductService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id ;
    private String title ;
    private double price ;
    private String category ;
    private String description ;
    private String image ;
}
