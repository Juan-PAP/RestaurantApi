package com.co.poo.restaurant.controller;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.usecase.ProductUseCase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity
                .ok()
                .body(productUseCase.getAllProducts());
    }

    @PostMapping
    public ResponseEntity <Product> createProduct (@RequestBody Product product){
        Product savedProduct = productUseCase.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteProduct (@PathVariable int id) {
        productUseCase.deleteProductById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity <Product> updateProductPrice (@PathVariable int id, @RequestBody Double newPrice) {
        Product product = productUseCase.updateProductPrice(id, newPrice);
        return ResponseEntity
                .ok()
                .body(product);
    }
}
