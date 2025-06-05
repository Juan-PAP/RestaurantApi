package com.co.poo.restaurant.services.order;

import com.co.poo.restaurant.model.Order;
import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.product.ProductRepository;
import com.co.poo.restaurant.services.product.SpringDataProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class PostgreSQLOrderService implements OrderRepository {

    private final SpringDataOrderRepository orderRepository;

    /**
     * Esta funcion retorna todas las ordenes
     *
     * @return Una lista que contiene todas las ordenes.
     */
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    /**
     * Esta funcion devuelve todas las ordenes que se encuentren activas.
     *
     * @return Una lista que contiene todas las ordenes activas.
     */
    @Override
    public List<Order> findByActiveTrue() {
        return orderRepository.findByActiveTrue();
    }
    /**
     * Esta funcion devuelve todas las ordenes que ya han sido entregadas.
     *
     * @return Una lista que contiene todas las ordenes entregadas.
     */
    @Override
    public List<Order> findByDeliveredTrue() {
        return orderRepository.findByDeliveredTrue();
    }

    /**
     * Esta funcion devuelve una orden por su id
     *
     * @param id es el id de la orden que se quiere consultar
     * @return regresa la orden que corresponde al id; null si no existe una orden que tenga ese id.
     */
    @Override
    public Order findOrderById(int id) {
        return orderRepository.findById(id)
                .orElse(null);
    }

    /**
     * Esta funcion devuelve una orden de una mesa en especifico siempre y cuando este activa
     *
     * @param tableNumber es el numero de mesa que se quiere buscar
     * @return una orden correspondiente a la mesa ingresada y que se encuentre activa
     */
    @Override
    public Order findByTableNumberAndActiveTrue(int tableNumber) {
        return orderRepository.findByTableNumberAndActiveTrue(tableNumber);
    }

    /**
     * Agrega una orden a la base de datos.
     *
     * @param order es la orden que se quiere ingresar
     * @return devuelve la misma orden que fue ingresada a la base de datos.
     */
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Esta funcion nos permite guardar los cambios realizados a una orden en la base de datos
     *
     * @param order es la orden que se quiere actualizar.
     * @return devuelve la misma orden despues de actualizar la base de datos.
     */
    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}
