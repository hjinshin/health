package com.health.springbootback.controller;

import com.health.springbootback.dto.MsgResponseDto;
import com.health.springbootback.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public class LogoutController {
    @Autowired
    private final AuthService authService;

    public LogoutController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/auth/kakao/logout")
    public ResponseEntity<MsgResponseDto> kakaoLogout(HttpServletResponse response,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.split(" ")[1];
        deleteTokenFromCookie(response, token);
        return authService.kakaoLogout(token);
    }

    private void deleteTokenFromCookie(HttpServletResponse response, String cookieHeader) {
        if(cookieHeader != null) {
            Cookie cookie = new Cookie("access_token", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}
