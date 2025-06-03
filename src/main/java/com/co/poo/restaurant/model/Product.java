package com.co.poo.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "La categoria del producto no puede ser nula")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "El precio del producto no puede ser nulo")
    @Column(nullable = false)
    private Double price;
}
