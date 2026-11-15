package com.project.scheduler_service.dto.feign;

import lombok.Data;

import java.util.List;

@Data
public class DummyProductListResponse {
    private List<DummyProductResponse> products;
    private int total;
    private int skip;
    private int limit;
}