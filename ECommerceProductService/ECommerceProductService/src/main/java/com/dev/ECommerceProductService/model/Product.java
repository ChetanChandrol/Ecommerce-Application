package com.dev.ECommerceProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "PRODUCTS")
public class Product extends BaseModel {
    private String title ;
    private double price ;
    private String description ;
    private String image ;
    @ManyToOne
    private Category category ;
}
