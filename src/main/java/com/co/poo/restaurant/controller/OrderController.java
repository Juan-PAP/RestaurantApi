package com.co.poo.restaurant.controller;

import com.co.poo.restaurant.model.Order;
import com.co.poo.restaurant.usecase.OrderUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderUseCase orderUseCase;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderUseCase.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderUseCase.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Order>> getOrderActive() {
        List<Order> activeOrders = orderUseCase.getActiveOrders();
        return ResponseEntity
                .ok()
                .body(activeOrders);
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<Order>> getOrderDelivered() {
        List<Order> deliveredOrders = orderUseCase.getDeliveredOrders();
        return ResponseEntity
                .ok()
                .body(deliveredOrders);
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity <Order> deliverOrder (@PathVariable int id) {
        Order order = orderUseCase.updateOrderStatus(id, "deliver");
        return ResponseEntity.ok(order);
    }

    @PutMapping ("/cancel/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable int id) {
        Order order = orderUseCase.updateOrderStatus(id, "cancel");
        return ResponseEntity.ok(order);
    }

    @PutMapping ("/close/{id}")
    public ResponseEntity <Order> closeOrder (@PathVariable int id,@Valid @RequestBody Map<String, Integer> discount) {
        Order order = orderUseCase.closeOrder (id, discount.get("discount"));
        return ResponseEntity.ok(order);

    }
}
