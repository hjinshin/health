package com.health.springbootback.controller;

import com.health.springbootback.dto.AdminAuthDto;
import com.health.springbootback.dto.UpdateRecordDto;
import com.health.springbootback.dto.UpdateResponseDto;
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

    // 프로필 업데이트
    @GetMapping("/api/profile")
    public UserInfoDto updateProfile(@RequestHeader("Cookie") String cookieHeader,
                                     @RequestParam String nickname) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.startsWith("access_token=")) {
                authToken = cookie.substring("access_token=".length());
                break;
            }
        }
        System.out.println(authToken);
        User user = authService.getKakaoProfile(authToken);
        userService.updateNickname(user.getUid(), nickname);
        return userService.findNicknameAndRoleById(user.getUid());
    }

    // 관리자 권한 인증
    @PostMapping("/api/auth")
    public ResponseEntity<String> adminAuth(@RequestHeader("Cookie") String cookieHeader,
                                            @RequestBody AdminAuthDto adminAuthDto) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("access_token=")) {
                authToken = cookie.trim().substring("access_token=".length());
                break;
            }
        }
        User user = authService.getKakaoProfile(authToken);

        return authService.adminAuth(user.getUid(), adminAuthDto.getPasswd());
    }

    // user 기록 업데이트
    @PutMapping("/api/records")
    public UpdateResponseDto updateRecords(@RequestHeader("Cookie") String cookieHeader,
                                           @RequestBody UpdateRecordDto updateRecordDto) {
        String[] cookies = cookieHeader.split(";");
        String authToken = null;

        for (String cookie : cookies) {
            if (cookie.trim().startsWith("access_token=")) {
                authToken = cookie.trim().substring("access_token=".length());
                break;
            }
        }
        Long adminId = authService.getKakaoProfile(authToken).getUid();
        User admin = userService.findMember(adminId);
        if(admin.getRole() != RoleType.ADMIN) {
            return new UpdateResponseDto(false, "권한이 존재하지 않습니다.");
        }

        User user = userService.findUserByNickname(updateRecordDto.getNickname());
        if(user == null) {
            return new UpdateResponseDto(false, updateRecordDto.getNickname() + "가 존재하지 않습니다.");
        }

        ExerciseType et = typeService.findERByExerciseName(updateRecordDto.getExerciseName());
        if(et == null) {
            return new UpdateResponseDto(false, updateRecordDto.getExerciseName() + "는 존재하지 않는 운동종목입니다.");
        }

        ExerciseRecord er = new ExerciseRecord(0, user, et, updateRecordDto.getValue(), null);
        recordService.updateRecords(er);
        return new UpdateResponseDto(true, "update records");
    }
}
