package com.project.product_service.rest;

import com.project.product_service.dto.feign.DummyProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dummyJsonClient", url = "${dummyjson.url}")
public interface DummyJsonClient {

    @PostMapping("/products/add")
    DummyProductDto addProduct(@RequestBody DummyProductDto request);

    @PutMapping("/products/{id}")
    DummyProductDto updateProduct(@PathVariable("id") Long id, @RequestBody DummyProductDto request);

    @DeleteMapping("/products/{id}")
    DummyProductDto deleteProduct(@PathVariable("id") Long id);
}