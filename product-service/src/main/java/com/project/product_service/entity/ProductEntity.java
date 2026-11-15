package com.project.product_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "mst_product")
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nama;

    @Column(nullable = false)
    private Double harga;

    @Column(nullable = false, length = 100)
    private String kategori;

    @Column(length = 100)
    private String brand;

    @Column
    private Double rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}