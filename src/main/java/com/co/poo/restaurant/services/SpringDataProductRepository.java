package com.co.poo.restaurant.services;

import com.co.poo.restaurant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SpringDataProductRepository extends JpaRepository<Product, Integer> {
}
