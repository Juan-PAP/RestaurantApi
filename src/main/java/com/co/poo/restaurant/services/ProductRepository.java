package com.co.poo.restaurant.services;

import com.co.poo.restaurant.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    List<Product> getProducts();

    Product findProductById(int id);

}
