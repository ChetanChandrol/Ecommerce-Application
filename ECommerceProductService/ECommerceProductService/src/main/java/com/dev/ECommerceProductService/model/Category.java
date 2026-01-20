package com.dev.ECommerceProductService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name ="CATEGORY")
public class Category extends BaseModel {
    private String categoryName;
}
