package com.project.scheduler_service.rest;

import com.project.scheduler_service.dto.request.ProductRequestDto;
import com.project.scheduler_service.dto.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/product/internal/import")
    BaseResponse<List<Object>> importProducts(@RequestBody List<ProductRequestDto> requests);
}