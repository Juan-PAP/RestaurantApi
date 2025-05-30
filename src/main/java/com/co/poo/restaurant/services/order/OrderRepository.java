package com.co.poo.restaurant.services.order;

import com.co.poo.restaurant.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {

    List<Order> getOrders();

    List<Order> findByActiveTrue();

    List<Order> findByDeliveredTrue();

    Order findOrderById(int id);

    Order createOrder(Order order);
}
