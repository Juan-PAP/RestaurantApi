package com.co.poo.restaurant.usecase;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return productRepository.save(product);
    }

    public void deleteProductById(int id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("El producto con el id: " + id + " no existe");
        }
        productRepository.deleteProductById(id);
    }

    public Product updateProductPrice(int id, Double newPrice) {
        if (newPrice == null || newPrice <= 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser nulo o negativo");
        }
        return Optional.ofNullable(productRepository.findProductById(id))
                .map(existingProduct -> {
                    existingProduct.setPrice(newPrice);
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new IllegalArgumentException("El producto con el id: " + id + " no existe."));
    }
}
