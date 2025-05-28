package com.co.poo.restaurant.usecase;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUseCase {

    private ProductRepository productRepository;

    @Autowired
    public ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }
}
