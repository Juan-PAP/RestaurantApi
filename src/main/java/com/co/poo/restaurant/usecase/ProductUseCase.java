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

    /**
     * Esta funcion invoca el metodo del service para consultar todos los productos y los expone al controller.
     *
     * @return una lista con todos los productos existentes.
     */
    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }
    /**
     * Esta funcion invoca el metodo del service para ingresar un producto a la base de datos.
     *
     * @param product es el producto que se quiere ingresar.
     * @return devuelve el resultado del metodo invocado.
     */
    public Product createProduct (Product product) {
        return productRepository.save(product);
    }
    /**
     * Esta funcion actualiza los datos de un producto en especifico dentro de la base de datos
     * verifica que el se ingrese correctamente un producto y este si exista dentro de la base de datos.
     *
     * @param id este es el id del producto que se va a modificar.
     * @param productUpdate es el producto con los datos actualizados.
     * @return regresa el producto modificado luego de actualizarlo en la base de datos.
     */
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
