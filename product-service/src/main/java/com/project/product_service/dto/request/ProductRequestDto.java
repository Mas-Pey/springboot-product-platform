package com.project.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Nama produk wajib diisi")
    private String nama;

    @NotNull(message = "Harga produk wajib diisi")
    @Positive(message = "Harga produk harus lebih dari 0")
    private Double harga;

    @NotBlank(message = "Kategori produk wajib diisi")
    private String kategori;

    @NotBlank(message = "Brand produk wajib diisi")
    private String brand;

    private Double rating;
}