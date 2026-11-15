package com.project.scheduler_service.service;

import com.project.scheduler_service.dto.feign.DummyProductListResponse;
import com.project.scheduler_service.dto.feign.DummyProductResponse;
import com.project.scheduler_service.dto.request.ProductRequestDto;
import com.project.scheduler_service.dto.response.BaseResponse;
import com.project.scheduler_service.rest.DummyJsonClient;
import com.project.scheduler_service.rest.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImportSchedulerService {

    private final DummyJsonClient dummyJsonClient;
    private final ProductClient productClient;

    @Value("${scheduler.import.batch-size:5}")
    private int batchSize;

    private int currentSkip = 0;

    public BaseResponse<List<Object>> doImport() {
        log.info("[Scheduler] Mengambil produk smartphones dari DummyJSON (limit={}, skip={})", batchSize, currentSkip);

        DummyProductListResponse response = dummyJsonClient.getSmartphones(batchSize, currentSkip);
        List<DummyProductResponse> dummyProducts = response.getProducts();

        if (dummyProducts == null || dummyProducts.isEmpty()) {
            log.info("[Scheduler] Tidak ada produk pada skip={}, reset skip ke 0", currentSkip);
            currentSkip = 0;
            return BaseResponse.ok("Tidak ada produk baru untuk diimpor (skip di-reset ke 0)", List.of());
        }

        currentSkip += batchSize;
        if (currentSkip >= response.getTotal()) {
            currentSkip = 0;
        }

        List<ProductRequestDto> requests = new ArrayList<>();
        for (DummyProductResponse item : dummyProducts) {
            requests.add(new ProductRequestDto(
                    item.getTitle(),
                    item.getPrice(),
                    item.getCategory(),
                    item.getBrand(),
                    item.getRating()
            ));
        }

        log.info("[Scheduler] Mengirim {} produk ke product-service via Feign", requests.size());
        BaseResponse<List<Object>> hasil = productClient.importProducts(requests);
        log.info("[Scheduler] Response dari product-service: {}", hasil.getMessage());

        return hasil;
    }
}