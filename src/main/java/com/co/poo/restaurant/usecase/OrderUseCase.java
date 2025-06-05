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

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un item.");
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

    public Order updateOrderStatus(int id, String action) {
        Order order = orderRepository.findOrderById(id);
        if (order == null || !order.getActive() || order.getDelivered()) {
            throw new IllegalArgumentException("Orden no encontrada o no se puede modificar.");
        }

        switch (action.toLowerCase()) {
            case "cancel":
                order.setActive(false);
                break;
            case "deliver":
                order.setDelivered(true);
                break;
            default:
                throw new IllegalArgumentException("Acción inválida: " + action);
        }

        return orderRepository.updateOrder(order);
    }

    public Order closeOrder(int id, Integer discount) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new IllegalArgumentException("Orden no encontrada.");
        }
        if (!(order.getActive() && order.getDelivered())) {
            throw new IllegalArgumentException("La orden debe estar activa y entregada para poder cerrarla.");
        }

        if (discount == null) {
            throw new IllegalArgumentException("El descuento no puede ser nulo.");
        }

        if (discount < 0 || discount > 10) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 10.");
        }

        var subtotal = order.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        var total = subtotal - (subtotal * discount / 100);

        order.setActive(false);
        order.setDiscount(discount);
        order.setTotal(total);
        orderRepository.updateOrder(order);

        return order;
    }
}
