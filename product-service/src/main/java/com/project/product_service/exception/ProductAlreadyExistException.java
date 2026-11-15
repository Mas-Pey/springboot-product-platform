package com.project.product_service.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String nama) {
        super("Produk dengan nama '" + nama + "' sudah ada");
    }
}