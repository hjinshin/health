package com.health.springbootback.controller;

import com.health.springbootback.dto.LoginResponseDto;
import com.health.springbootback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class LoginController {
    private final AuthService authService;
    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<LoginResponseDto> kakaoCallback(@RequestParam String code) {
        //System.out.println("카카오 인가코드: " + code);
        String kakaoAccessToken = authService.getKakaoAccessToken(code);
        //System.out.println("카카오 엑세스토큰: " + kakaoAccessToken);

        return authService.kakaoLogin(kakaoAccessToken);
    }
}
