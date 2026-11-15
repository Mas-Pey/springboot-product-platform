package com.project.auth_service.controller;

import com.project.auth_service.dto.request.AuthRequestDto;
import com.project.auth_service.dto.response.AuthResponseDto;
import com.project.auth_service.dto.response.BaseResponse;
import com.project.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public BaseResponse<String> register(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return BaseResponse.ok(authService.register(authRequestDto));
    }

    @PostMapping("/login")
    public BaseResponse<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return BaseResponse.ok(authService.login(authRequestDto));
    }
}