package com.co.poo.restaurant.services;

import com.co.poo.restaurant.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class PostgreSQLProductService implements ProductRepository{

    private final SpringDataProductRepository productRepository;

    @Autowired
    public PostgreSQLProductService(SpringDataProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElse(null);
    }
}
