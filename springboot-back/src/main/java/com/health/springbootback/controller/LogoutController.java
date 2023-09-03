package com.health.springbootback.controller;

import com.health.springbootback.dto.MsgResponseDto;
import com.health.springbootback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

//@CrossOrigin
@RestController
public class LogoutController {
    @Autowired
    private final AuthService authService;

    public LogoutController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/kakao/logout")
    public ResponseEntity<MsgResponseDto> kakaoLogout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.split(" ")[1];
        return authService.kakaoLogout(token);
    }
}
