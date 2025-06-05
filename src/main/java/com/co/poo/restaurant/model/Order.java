package com.co.poo.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private Integer orderId;

    @NotNull(message = "El numero de mesa no puede ser nulo")
    @Min(value =1, message = "El numero de mesa debe ser mayor o igual a 1")
    @Max(value = 24, message = "El numero de mesa debe ser menor o igual a 24")
    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @NotNull(message = "El descuento no puede ser nulo")
    @Min(value = 0, message = "El descuento debe ser mayor o igual a 0")
    @Max(value = 10, message = "El descuento debe ser menor o igual a 10")
    @Column(nullable = false)
    private Integer discount;

    @NotNull(message = "El total no puede ser nulo")
    @Column(nullable = false)
    private Double total;

    @NotNull(message = "El estado de la orden no puede ser nulo")
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull(message = "El estado de la entrega no puede ser nulo")
    @Column(name = "delivered", nullable = false)
    private Boolean delivered;

    @NotNull(message = "La lista de items no puede ser nula")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_order", nullable = false)
    private List<OrderItem> items = new ArrayList<>();
}
