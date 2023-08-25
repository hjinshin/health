package com.health.springbootback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.springbootback.dto.AdminAuthDto;
import com.health.springbootback.dto.UpdateRecordDto;
import com.health.springbootback.dto.UserInfoDto;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.entity.ExerciseType;
import com.health.springbootback.entity.User;
import com.health.springbootback.enums.RoleType;
import com.health.springbootback.service.AuthService;
import com.health.springbootback.service.RecordService;
import com.health.springbootback.service.UserService;
import com.health.springbootback.service.TypeService;
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
    @Autowired
    private final TypeService typeService;
    @Autowired
    private final RecordService recordService;

    public ProfileController(AuthService authService, UserService userService, TypeService typeService, RecordService recordService) {
        this.authService = authService;
        this.userService = userService;
        this.typeService = typeService;
        this.recordService = recordService;
    }

    // 내 프로필 업데이트
    @PutMapping("/api/profile")
    public ResponseEntity<UserInfoDto> updateProfile(@RequestHeader("Cookie") String cookieHeader,
                                                     @RequestParam String nickname) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.startsWith("access_token=")) {
                authToken = cookie.substring("access_token=".length());
                break;
            }
        }

        try {
            User user = authService.getKakaoProfile(authToken);
            userService.updateNickname(user.getUid(), nickname);
            UserInfoDto userInfoDto = userService.findNicknameAndRoleById(user.getUid());
            return ResponseEntity.ok(userInfoDto);
        } catch (HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new UserInfoDto(e.getMessage(), null));
        }



    }

    // 관리자 권한 인증
    @PostMapping("/api/auth")
    public ResponseEntity<String> adminAuth(@RequestHeader("Cookie") String cookieHeader,
                                            @RequestBody AdminAuthDto adminAuthDto){
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("access_token=")) {
                authToken = cookie.trim().substring("access_token=".length());
                break;
            }
        }
        try {
            User user = authService.getKakaoProfile(authToken);
            return authService.adminAuth(user.getUid(), adminAuthDto.getPasswd());
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // user 기록 업데이트
    @PutMapping("/api/records")
    public ResponseEntity<String> updateRecords(@RequestHeader("Cookie") String cookieHeader,
                                           @RequestBody UpdateRecordDto updateRecordDto) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("access_token=")) {
                authToken = cookie.trim().substring("access_token=".length());
                break;
            }
        }
        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }

            User user = userService.findUserByNickname(updateRecordDto.getNickname());
            if(user == null) {
                return ResponseEntity.badRequest().body( updateRecordDto.getNickname() + "가 존재하지 않습니다.");
            }

            ExerciseType et = typeService.findERByExerciseName(updateRecordDto.getExerciseName());
            if(et == null) {
                return ResponseEntity.badRequest().body(updateRecordDto.getExerciseName() + "는 존재하지 않는 운동종목입니다.");
            }

            ExerciseRecord er = new ExerciseRecord(0, user, et, updateRecordDto.getValue(), null);
            recordService.updateRecords(er);
            return ResponseEntity.ok("update records");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
