package com.co.poo.restaurant.services.product;

import com.co.poo.restaurant.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class PostgreSQLProductService implements ProductRepository {

    private final SpringDataProductRepository productRepository;

    @Autowired
    public PostgreSQLProductService(SpringDataProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    /**
     * Esta funcion retorna todos los productos que hayan en la base de datos.
     *
     * @return una lista que contiene todos los productos.
     */

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    /**
     * Esta funcion busca un producto en especifico segun su id.
     *
     * @param id es el id del producto que se quiere encontrar.
     * @return el producto que coincide con el id ingresado; null si no hay ningun producto con ese id
     */

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    /**
     * Esta funcion permite guardar un producto nuevo dentro de la base de datos.
     *
     * @param product es el producto que se quiere ingresar.
     * @return devuelve el mismo producto luego de guardarlo en la base de datos.
     */

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Esta funcion elimina un producto en especifico de la base de datos segun su id.
     *
     * @param id es el id de el producto que se quiere eliminar.
     */

    @Override
    public void deleteProductById(int id) {
        productRepository.deleteById(id);
    }
}
