package com.co.poo.restaurant.usecase;

import com.co.poo.restaurant.model.Order;
import com.co.poo.restaurant.model.OrderItem;
import com.co.poo.restaurant.model.Product;
import com.co.poo.restaurant.services.order.OrderRepository;
import com.co.poo.restaurant.services.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderUseCase {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getOrders();
    }

    @Transactional
    public Order createOrder(Order order) {
        if (orderRepository.findByTableNumberAndActiveTrue(order.getTableNumber()) != null) {
            throw new IllegalArgumentException("Ya existe una orden activa para la mesa " + order.getTableNumber());
        }

        for (OrderItem item : order.getItems()) {
            int productId = item.getProduct().getIdProduct();
            Product product = productRepository.findProductById(productId);

            if (product == null) {
                throw new IllegalArgumentException("Producto con ID " + productId + " no encontrado.");
            }

            item.setProduct(product);
        }

        return orderRepository.createOrder(order);
    }

    public List<Order> getActiveOrders() {
        return orderRepository.findByActiveTrue();
    }

    public List<Order> getDeliveredOrders() {
        return orderRepository.findByDeliveredTrue();
    }

    public Order cancelOrder(int id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null || !order.isActive() || order.isDelivered()) {
            throw new IllegalArgumentException("Orden no encontrada o no se puede cancelar.");
        }
        order.setActive(false);
        return orderRepository.updateOrder(order);
    }

    public Order deliverOrder(int id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null || !order.isActive() || order.isDelivered()) {
            throw new IllegalArgumentException("Orden no encontrada o ya ha sido entregada.");
        }
        order.setDelivered(true);
        return orderRepository.updateOrder(order);
    }
}
