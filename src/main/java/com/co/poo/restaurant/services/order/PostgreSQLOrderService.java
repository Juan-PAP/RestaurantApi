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


    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByActiveTrue() {
        return orderRepository.findByActiveTrue();
    }

    @Override
    public List<Order> findByDeliveredTrue() {
        return orderRepository.findByDeliveredTrue();
    }

    @Override
    public Order findOrderById(int id) {
        return orderRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
