package com.project.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {
    private String fileName;
    private int totalData;
    private String downloadUrl;
}