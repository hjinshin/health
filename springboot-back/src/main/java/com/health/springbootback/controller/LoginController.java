package com.health.springbootback.controller;

import com.health.springbootback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String kakaoCallback(@RequestParam String code) {

        //System.out.println("카카오 인가코드: " + code);
        String kakaoAccessToken = authService.getKakaoAccessToken(code);
        
        //System.out.println("카카오 엑세스토큰: " + kakaoAccessToken);
        String test = authService.getKakaoProfile(kakaoAccessToken);
        //System.out.println("카카오 정보: " + test);
        
        // return 카카오로그인처리(성공, 실패 결과를 client에 전송)
        return "카카오 토큰 : ";
    }
}
