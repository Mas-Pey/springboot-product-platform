package com.project.product_service.dto.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DummyProductDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String brand;
    private Double rating;
    private Boolean isDeleted;
}
