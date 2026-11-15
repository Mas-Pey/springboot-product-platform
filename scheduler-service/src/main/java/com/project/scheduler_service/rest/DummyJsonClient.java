package com.project.scheduler_service.rest;

import com.project.scheduler_service.dto.feign.DummyProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dummyJsonClient", url = "${dummyjson.url}")
public interface DummyJsonClient {

    @GetMapping("/products/category/smartphones")
    DummyProductListResponse getSmartphones(
            @RequestParam("limit") int limit,
            @RequestParam("skip") int skip
    );
}