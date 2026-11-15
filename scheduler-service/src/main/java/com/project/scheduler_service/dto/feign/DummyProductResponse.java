package com.project.scheduler_service.dto.feign;

import lombok.Data;

@Data
public class DummyProductResponse {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String brand;
    private Double rating;
}