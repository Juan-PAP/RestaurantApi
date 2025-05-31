package com.co.poo.restaurant.services.product;

import com.co.poo.restaurant.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    List<Product> getProducts();

    Product findProductById(int id);

    Product saveProduct (Product product);

    void deleteProductById (int id);

}
