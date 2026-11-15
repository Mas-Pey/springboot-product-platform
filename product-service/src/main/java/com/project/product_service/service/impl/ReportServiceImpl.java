package com.project.product_service.service.impl;

import com.project.product_service.dto.response.ReportResponseDto;
import com.project.product_service.entity.ProductEntity;
import com.project.product_service.repository.ProductRepository;
import com.project.product_service.service.ReportService;
import com.project.product_service.util.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceImpl implements ReportService {

    private final ProductRepository productRepository;
    private final MinioUtil minioUtil;

    @Override
    public ReportResponseDto generateReport() {
        LocalDateTime end = LocalDateTime.now();
        // LocalDateTime start = end.minusDays(1);
        LocalDateTime start = end.minusHours(1);
        List<ProductEntity> products = productRepository.findByCreatedAtBetweenOrderByIdAsc(start, end);

        StringBuilder csv = new StringBuilder("id,nama,harga,kategori,brand,rating,created_at\n");
        for (ProductEntity p : products) {
            csv.append(p.getId()).append(',')
                    .append('"').append(p.getNama().replace("\"", "\"\"")).append('"').append(',')
                    .append(p.getHarga()).append(',')
                    .append(p.getKategori()).append(',')
                    .append(p.getBrand() == null ? "" : p.getBrand()).append(',')
                    .append(p.getRating() == null ? "" : p.getRating()).append(',')
                    .append(p.getCreatedAt()).append('\n');
        }

        String fileName = "product-report-" + System.currentTimeMillis() + ".csv";
        minioUtil.upload(fileName, csv.toString().getBytes(StandardCharsets.UTF_8), "text/csv");
        String downloadUrl = minioUtil.getDownloadUrl(fileName);

        log.info("Laporan CSV '{}' berhasil dibuat ({} produk)", fileName, products.size());
        return new ReportResponseDto(fileName, products.size(), downloadUrl);
    }
}