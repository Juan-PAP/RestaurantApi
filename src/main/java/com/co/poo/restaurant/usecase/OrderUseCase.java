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

    /**
     * Esta funcion invoca el metodo del service para consultar todas las ordenes
     * y lo expone al controller.
     *
     * @return una lista con todas las ordenes existentes.
     */

    public List<Order> getAllOrders() {
        return orderRepository.getOrders();
    }

    /**
     * Esta funcion sirve para crear una orden para una mesa en caso de que no haya ninguna activa
     * y verifica que todos los productos ordenados existan.
     *
     * @param order es la orden que se quiere crear.
     * @return devuelve la misma orden luego de crearla en la base de datos.
     */

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

    /**
     * Esta funcion invoca el metodo del service para consultar todas las ordenes activas
     * y las expone al controller.
     *
     * @return devuelve una lista que contiene todas las ordenes activas.
     */

    public List<Order> getActiveOrders() {
        return orderRepository.findByActiveTrue();
    }

    /**
     * Esta funcion invoca el metodo del service para consultar todas las ordenes entregadas
     * y las expone al controller.
     *
     * @return devuelve una lista con todas las ordenes entregadas.
     */

    public List<Order> getDeliveredOrders() {
        return orderRepository.findByDeliveredTrue();
    }

    /**
     * Esta funcion sirve para modificar el estado de una orden, ya sea para cancelarla o entregarla
     * verifica que la orden si exista y que sea modificable; y verifica que la accion sea una accion valida
     * y la actualiza en la base de datos.
     *
     * @param id es el id de la orden que se quiere modificar.
     * @param action es un string que dice la accion que se quiere realizar.
     * @return devuelve la misma orden luego de actualizarla en la base de datos.
     */

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

    /**
     * Esta funcion sirve para calcular el total de una orden y desactivarla
     * verifica que la orden exista, este activa y ya haya sido entregada para poder totalizarla
     * y verifica que el descuento ingresado sea valido y calcula el total en base a el.
     *
     * @param id es el id de la orden que se quiere cerrar.
     * @param discount es el % de descuento que se le va a aplicar al total de la orden
     * @return regresa la misma orden luego de guardar los cambios en la base de datos.
     */

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
