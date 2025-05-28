package com.co.poo.restaurant.services.order;

import com.co.poo.restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataOrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByActiveTrue();
    List<Order> findByDeliveredTrue();
}
