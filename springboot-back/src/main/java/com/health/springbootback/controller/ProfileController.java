package com.health.springbootback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.springbootback.dto.AdminAuthDto;
import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.entity.User;
import com.health.springbootback.service.AuthService;
import com.health.springbootback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

@CrossOrigin
@RestController
public class ProfileController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;

    public ProfileController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PutMapping("/api/profile")
    public ResponseEntity<UserInfoDto> updateProfile(@CookieValue(name = "access_token") String cookie,
                                                     @RequestParam String nickname) {
        try {
            User user = authService.getKakaoProfile(cookie);

            // 닉네임이 이미 존재할 경우
            if(userService.existNickname(nickname))
                return ResponseEntity.badRequest().body(new UserInfoDto("이미 존재하는 닉네임입니다.", null));
            userService.updateNickname(user.getUid(), nickname);
            UserInfoDto userInfoDto = userService.findNicknameAndRoleById(user.getUid());
            return ResponseEntity.ok(userInfoDto);
        } catch (HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new UserInfoDto(e.getMessage(), null));
        }
    }

    @GetMapping("/api/profile")
    public ResponseEntity<String> getProfile(@CookieValue(name = "access_token") String cookie) {
        try {
            User user = authService.getKakaoProfile(cookie);
            return ResponseEntity.ok(String.valueOf(user.getRole()));
        } catch (HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 관리자 권한 인증
    @PostMapping("/api/auth")
    public ResponseEntity<String> adminAuth(@CookieValue(name = "access_token") String cookie,
                                            @RequestBody AdminAuthDto adminAuthDto){
        System.out.println(cookie);
        try {
            User user = authService.getKakaoProfile(cookie);
            return authService.adminAuth(user.getUid(), adminAuthDto.getPasswd());
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
