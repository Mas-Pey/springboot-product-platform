package com.project.product_service.controller;

import com.project.product_service.dto.request.ProductRequestDto;
import com.project.product_service.dto.response.BaseResponse;
import com.project.product_service.dto.response.ProductResponseDto;
import com.project.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponseDto>> create(
            @Valid @RequestBody ProductRequestDto request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        log.info("User '{}' membuat produk baru: {}", userId, request.getNama());
        ProductResponseDto created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.ok("Produk berhasil dibuat", created));
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductResponseDto> getById(@PathVariable Long id) {
        return BaseResponse.ok(productService.getById(id));
    }

    @GetMapping
    public BaseResponse<List<ProductResponseDto>> getAll(
            @RequestParam(required = false) String huruf) {
        return BaseResponse.ok(productService.getAll(huruf));
    }

    @PutMapping("/{id}")
    public BaseResponse<ProductResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDto request) {
        return BaseResponse.ok("Produk berhasil diubah", productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<String> delete(@PathVariable Long id) {
        productService.delete(id);
        return BaseResponse.ok("Produk berhasil dihapus", null);
    }

    @PostMapping("/internal/import")
    public BaseResponse<List<ProductResponseDto>> importInternal(
            @RequestBody List<ProductRequestDto> requests) {
        log.info("Menerima request impor internal sebanyak {} produk", requests.size());
        List<ProductResponseDto> saved = productService.importProducts(requests);
        return BaseResponse.ok(saved.size() + " produk berhasil diimpor", saved);
    }
}