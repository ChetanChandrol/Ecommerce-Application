package com.dev.ECommerceProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "PRODUCTS")
public class Product extends BaseModel {

    private String title ;
    private String description ;
    private String image ;
    private Double price;
    private String brand;
    @ManyToOne
    private Category category ;
}
