package com.co.poo.restaurant.usecase;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Product createProduct (Product product) {
        return productRepository.saveProduct(product);
    }

    public void deleteProductById(int id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("El producto con el id: " + id + " no existe");
        }
        productRepository.deleteProductById(id);
    }
}
