package com.co.poo.restaurant.controller;

import com.co.poo.restaurant.model.Order;
import com.co.poo.restaurant.usecase.OrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
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
    public ResponseEntity <List<Order>> getOrderDelivered(){
        List <Order> deliveredOrders = orderUseCase.getDeliveredOrders();
        return ResponseEntity
                .ok()
                .body(deliveredOrders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable int id) {
        Order order = orderUseCase.cancelOrder(id);
        return ResponseEntity
                .ok()
                .body(order);
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity<Order> deliverOrder(@PathVariable int id) {
        Order order = orderUseCase.deliverOrder(id);
        return ResponseEntity
                .ok()
                .body(order);
    }
}
