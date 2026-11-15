package com.project.product_service.repository;

import com.project.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByNamaContainingIgnoreCase(String huruf);
    boolean existsByNamaIgnoreCase(String nama);
    List<ProductEntity> findByCreatedAtBetweenOrderByIdAsc(LocalDateTime start, LocalDateTime end);
}