package com.project.auth_service.service;

import com.project.auth_service.dto.request.AuthRequestDto;
import com.project.auth_service.dto.response.AuthResponseDto;

public interface AuthService {
    String register(AuthRequestDto authRequestDto);
    AuthResponseDto login(AuthRequestDto authRequestDto);
}