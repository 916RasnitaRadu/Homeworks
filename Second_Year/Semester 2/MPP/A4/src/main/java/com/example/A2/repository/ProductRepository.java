package com.example.A2.repository;

import com.example.A2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);

}
