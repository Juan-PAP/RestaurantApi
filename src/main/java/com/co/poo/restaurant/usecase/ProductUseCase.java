package com.co.poo.restaurant.usecase;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUseCase {

    private final ProductRepository productRepository;

    @Autowired
    public ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }

    public Product createProduct (Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product productUpdate) {
        if (productUpdate == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        var product = productRepository.findProductById(id);

        if (product == null) {
            throw new IllegalArgumentException("El producto con el id: " + id + " no existe.");
        }

        product.setName(productUpdate.getName());
        product.setCategory(productUpdate.getCategory());
        product.setPrice(productUpdate.getPrice());

        return productRepository.save(product);
    }
}
