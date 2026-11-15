package com.project.scheduler_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String nama;
    private Double harga;
    private String kategori;
    private String brand;
    private Double rating;
}