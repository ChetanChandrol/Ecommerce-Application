package com.dev.ECommerceProductService.repository;

import com.dev.ECommerceProductService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
