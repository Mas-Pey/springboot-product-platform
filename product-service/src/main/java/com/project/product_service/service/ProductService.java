package com.project.product_service.service;

import com.project.product_service.dto.request.ProductRequestDto;
import com.project.product_service.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductRequestDto request);
    ProductResponseDto getById(Long id);
    List<ProductResponseDto> getAll(String huruf);
    ProductResponseDto update(Long id, ProductRequestDto request);
    void delete(Long id);
    List<ProductResponseDto> importProducts(List<ProductRequestDto> requests);
}