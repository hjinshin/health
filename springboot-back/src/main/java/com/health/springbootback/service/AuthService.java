package com.health.springbootback.service;

import com.health.springbootback.dto.LoginResponseDto;
import com.health.springbootback.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String getKakaoAccessToken(String code);
    User getKakaoProfile(String kakaoAccessToken);
    ResponseEntity<LoginResponseDto> kakaoLogin(String kakaoAccessToken);
}
