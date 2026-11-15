package com.project.scheduler_service.exception;

import com.project.scheduler_service.dto.response.BaseResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseResponse<Object>> handleFeignException(FeignException exception) {
        log.error("Feign error di scheduler-service: status={}, message={}", exception.status(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.error("Gagal terhubung ke product-service "));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception exception) {
        log.error("Error di scheduler-service: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(BaseResponse.error(exception.getMessage()));
    }
}