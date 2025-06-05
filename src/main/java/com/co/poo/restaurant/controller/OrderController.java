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

    /**
     * Endpoint para consultar todas las ordenes.
     *
     */

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderUseCase.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Endpoint para crear orden.
     *
     * @param order es la orden que se va a crear.
     */

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderUseCase.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    /**
     * Endpoint para consultar todas las ordenes activas.
     *
     */

    @GetMapping("/active")
    public ResponseEntity<List<Order>> getOrderActive() {
        List<Order> activeOrders = orderUseCase.getActiveOrders();
        return ResponseEntity
                .ok()
                .body(activeOrders);
    }

    /**
     * Endpoint para consultar todas las ordenes entregadas.
     *
     */

    @GetMapping("/delivered")
    public ResponseEntity<List<Order>> getOrderDelivered() {
        List<Order> deliveredOrders = orderUseCase.getDeliveredOrders();
        return ResponseEntity
                .ok()
                .body(deliveredOrders);
    }
    /**
     * Endpoint para poner el estado de una orden en "entregado"
     *
     * @param id es el id de la orden que se va a modificar
     */

    @PutMapping("/deliver/{id}")
    public ResponseEntity <Order> deliverOrder (@PathVariable int id) {
        Order order = orderUseCase.updateOrderStatus(id, "deliver");
        return ResponseEntity.ok(order);
    }

    /**
     * Endpoint para cancelar una orden que no haya sido entregada.
     *
     * @param id es el id de la orden que se va a modificar.
     */

    @PutMapping ("/cancel/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable int id) {
        Order order = orderUseCase.updateOrderStatus(id, "cancel");
        return ResponseEntity.ok(order);
    }

    /**
     * Endpoint para cerrar una orden y calcularle el total.
     *
     * @param id es el id de la orden que se va a cerrar.
     * @param discount es el descuento que se le va a aplicar al total.
     */

    @PutMapping ("/close/{id}")
    public ResponseEntity <Order> closeOrder (@PathVariable int id,@Valid @RequestBody Map<String, Integer> discount) {
        Order order = orderUseCase.closeOrder (id, discount.get("discount"));
        return ResponseEntity.ok(order);

    }
}
