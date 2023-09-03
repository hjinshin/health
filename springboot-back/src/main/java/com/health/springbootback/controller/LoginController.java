package com.health.springbootback.controller;

import com.health.springbootback.dto.LoginResponseDto;
import com.health.springbootback.service.AuthService;
import com.health.springbootback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = {"*"})
@RestController
public class LoginController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;
    public LoginController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<LoginResponseDto> kakaoCallback(@RequestParam String code){
        System.out.println("카카오 인가코드: " + code);
        String kakaoAccessToken = authService.getKakaoAccessToken(code);
        System.out.println("카카오 엑세스토큰: " + kakaoAccessToken);

        return authService.kakaoLogin(kakaoAccessToken);
    }
/*
    @GetMapping("/api/access-token")
    public ResponseEntity<?> login(@RequestHeader("Cookie") String cookieHeader) {
        try {
            String authToken = getAccessToken(cookieHeader);
            System.out.println(authToken);

            User user = authService.getKakaoProfile(authToken);
            UserInfoDto userInfoDto = userService.findNicknameAndRoleById(user.getUid());
            LoginResponseDto loginResponseDto = new LoginResponseDto(true, userInfoDto);
            return ResponseEntity.ok().body(loginResponseDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String getAccessToken(String cookieHeader) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("access_token=")) {
                authToken = cookie.trim().substring("access_token=".length());
                break;
            }
        }
        return authToken;
    }

    @ExceptionHandler({ MissingRequestHeaderException.class })
    public ResponseEntity<?> handleException1(MissingRequestHeaderException e) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(false, null);
        return ResponseEntity.ok().body(loginResponseDto);
    }
 */
}
