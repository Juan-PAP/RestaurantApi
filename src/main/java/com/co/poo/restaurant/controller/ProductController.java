package com.co.poo.restaurant.controller;

import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.usecase.ProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * Endpoint para obtener todos los productos.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity
                .ok()
                .body(productUseCase.getAllProducts());
    }

    /**
     * Endpoint para crear un producto.
     *
     * @param product es el producto que se va a ingresar.
     */

    @PostMapping
    public ResponseEntity <Product> createProduct (@RequestBody Product product){
        Product savedProduct = productUseCase.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    /**
     * Endpoint para actualizar los datos de un producto.
     *
     * @param id es el id del producto que se va a actualizar.
     * @param productUpdate es el producto con los datos nuevos.
     */

    @PutMapping("/{id}")
    public ResponseEntity <Product> updateProduct(@PathVariable int id, @Valid @RequestBody Product productUpdate) {
        Product product = productUseCase.updateProduct(id, productUpdate);
        return ResponseEntity
                .ok()
                .body(product);
    }
}
