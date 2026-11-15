package com.project.product_service.service.impl;

import com.project.product_service.dto.request.ProductRequestDto;
import com.project.product_service.dto.response.ProductResponseDto;
import com.project.product_service.entity.ProductEntity;
import com.project.product_service.exception.ProductAlreadyExistException;
import com.project.product_service.exception.ProductNotFoundException;
import com.project.product_service.repository.ProductRepository;
import com.project.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponseDto create(ProductRequestDto request) {
        String nama = request.getNama().trim();
        if (productRepository.existsByNamaIgnoreCase(nama)) {
            throw new ProductAlreadyExistException(nama);
        }

        ProductEntity entity = new ProductEntity();
        entity.setNama(nama);
        entity.setHarga(request.getHarga());
        entity.setKategori(request.getKategori());
        entity.setBrand(request.getBrand());
        entity.setRating(request.getRating());
        entity.setCreatedAt(LocalDateTime.now());

        productRepository.save(entity);
        return ProductResponseDto.from(entity);
    }

    @Override
    public ProductResponseDto getById(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductResponseDto.from(entity);
    }

    @Override
    public List<ProductResponseDto> getAll(String huruf) {
        List<ProductEntity> semua;
        if (huruf == null || huruf.isBlank()) {
            semua = productRepository.findAll();
        } else {
            semua = productRepository.findByNamaContainingIgnoreCase(huruf);
        }

        List<ProductResponseDto> hasil = new ArrayList<>();
        for (ProductEntity data : semua) {
            hasil.add(ProductResponseDto.from(data));
        }
        return hasil;
    }

    @Override
    public ProductResponseDto update(Long id, ProductRequestDto request) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        String namaBaru = request.getNama().trim();
        if (!entity.getNama().equalsIgnoreCase(namaBaru) && productRepository.existsByNamaIgnoreCase(namaBaru)) {
            throw new ProductAlreadyExistException(namaBaru);
        }

        entity.setNama(namaBaru);
        entity.setHarga(request.getHarga());
        entity.setKategori(request.getKategori());
        entity.setBrand(request.getBrand());
        entity.setRating(request.getRating());

        productRepository.save(entity);
        return ProductResponseDto.from(entity);
    }

    @Override
    public void delete(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(entity);
    }

    @Override
    public List<ProductResponseDto> importProducts(List<ProductRequestDto> requests) {
        List<ProductResponseDto> tersimpan = new ArrayList<>();

        for (ProductRequestDto req : requests) {
            if (req.getNama() == null || req.getNama().isBlank()) {
                continue;
            }
            String nama = req.getNama().trim();

            // Rating < 3 tidak diimpor
            if (req.getRating() != null && req.getRating() < 3.0) {
                log.info("Skip impor produk '{}' karena rating {} < 3.0", nama, req.getRating());
                continue;
            }

            // Cek duplikat
            if (productRepository.existsByNamaIgnoreCase(nama)) {
                log.info("Skip impor produk '{}' karena sudah ada di database", nama);
                continue;
            }

            ProductEntity entity = new ProductEntity();
            entity.setNama(nama);
            entity.setHarga(req.getHarga());
            entity.setKategori(req.getKategori());
            entity.setBrand(req.getBrand() != null ? req.getBrand() : "-");
            entity.setRating(req.getRating());
            entity.setCreatedAt(LocalDateTime.now());

            productRepository.save(entity);
            log.info("Produk impor '{}' (brand: {}, rating: {}) berhasil disimpan", nama, entity.getBrand(), entity.getRating());
            tersimpan.add(ProductResponseDto.from(entity));
        }

        return tersimpan;
    }
}