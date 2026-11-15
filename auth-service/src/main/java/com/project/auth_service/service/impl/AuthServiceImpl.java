package com.project.auth_service.service.impl;

import com.project.auth_service.dto.request.AuthRequestDto;
import com.project.auth_service.dto.response.AuthResponseDto;
import com.project.auth_service.entity.UserEntity;
import com.project.auth_service.exception.UnauthorizedException;
import com.project.auth_service.exception.UserAlreadyExistException;
import com.project.auth_service.repository.UserRepository;
import com.project.auth_service.service.AuthService;
import com.project.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String register(AuthRequestDto authRequestDto) {
        if (userRepository.existsByUsername(authRequestDto.getUsername())) {
            throw new UserAlreadyExistException(authRequestDto.getUsername());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(authRequestDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));
        userRepository.save(userEntity);

        return "User " + userEntity.getUsername() + " berhasil didaftarkan";
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        UserEntity userEntity = userRepository.findByUsername(authRequestDto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Username atau Password salah"));

        if (!passwordEncoder.matches(authRequestDto.getPassword(), userEntity.getPassword())) {
            throw new UnauthorizedException("Username atau Password salah");
        }

        String token = jwtUtil.generateToken(userEntity.getUsername());

        return new AuthResponseDto(userEntity.getUsername(), token);
    }
}