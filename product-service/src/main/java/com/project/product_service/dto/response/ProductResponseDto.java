package com.project.product_service.dto.response;

import com.project.product_service.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String nama;
    private Double harga;
    private String kategori;
    private String brand;
    private Double rating;
    private LocalDateTime createdAt;

    public static ProductResponseDto from(ProductEntity entity) {
        return new ProductResponseDto(
                entity.getId(),
                entity.getNama(),
                entity.getHarga(),
                entity.getKategori(),
                entity.getBrand(),
                entity.getRating(),
                entity.getCreatedAt()
        );
    }
}