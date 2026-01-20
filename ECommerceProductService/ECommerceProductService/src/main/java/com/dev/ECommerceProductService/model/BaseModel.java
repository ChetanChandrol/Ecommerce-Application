package com.dev.ECommerceProductService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
//    @GeneratedValue(generator = "uuidGenerator")
//    @GenericGenerator(name = "uuidGenerator", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, columnDefinition = "binary(16)", updatable = false)
    private UUID id;
}
