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
    private int orderId;

    @Column(name = "table_number", nullable = false)
    private int tableNumber;

    @Column(nullable = false)
    private int discount;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "delivered", nullable = false)
    private boolean delivered;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_order", nullable = false)
    private List<OrderItem> items = new ArrayList<>();
}
