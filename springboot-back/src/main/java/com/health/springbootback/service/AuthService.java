package com.health.springbootback.service;

public interface AuthService {
    String getKakaoAccessToken(String code);
    String getKakaoProfile(String kakaoAccessToken);
}
