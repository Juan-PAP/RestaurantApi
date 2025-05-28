package com.co.poo.restaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private int orderId; //Identificador unico

    @Column(name = "table_number", nullable = false)
    private int tableNumber; //Numero de la mesa

    @Column(nullable = false)
    private int discount;

    @Column(name = "is_active", nullable = false)
    private boolean isActive; //Si la orden se encuentra activa

    @Column(name = "is_delivered", nullable = false)
    private boolean isDelivered;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}
