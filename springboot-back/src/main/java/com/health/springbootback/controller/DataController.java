package com.health.springbootback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.springbootback.dto.CategoryDto;
import com.health.springbootback.dto.SubCategoryDto;
import com.health.springbootback.dto.UpdateRecordDto;
import com.health.springbootback.entity.*;
import com.health.springbootback.enums.RoleType;
import com.health.springbootback.service.AuthService;
import com.health.springbootback.service.RecordService;
import com.health.springbootback.service.CategoryService;
import com.health.springbootback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@CrossOrigin
@RestController
public class DataController {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final CategoryService categoryService;
    @Autowired
    private final RecordService recordService;

    public DataController(AuthService authService, UserService userService, CategoryService categoryService, RecordService recordService) {
        this.authService = authService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.recordService = recordService;
    }

    // user 기록 업데이트
    @PutMapping("/api/record")
    public ResponseEntity<String> updateRecords(@RequestHeader("Cookie") String cookieHeader,
                                                @RequestBody UpdateRecordDto updateRecordDto) {
        String authToken = getAccessToken(cookieHeader);

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

            ExerciseSubCategory et = categoryService.findERByExerciseName(updateRecordDto.getExerciseName());
            if(et == null) {
                return ResponseEntity.badRequest().body(updateRecordDto.getExerciseName() + "는 존재하지 않는 운동종목입니다.");
            }

            ExerciseRecord er = new ExerciseRecord(0, user, et, updateRecordDto.getValue(), null);
            // 기록 업데이트
            recordService.updateRecords(er);
            // 최고기록 업데이트
            PersonalBestRecord pbr = recordService.findByUidAndEid(er.getUid().getUid(), er.getEid().getEid());
            if(pbr == null)
                recordService.updatePBR(new PersonalBestRecord(0, er.getUid(), er.getEid(), er, er.getRecordValue(), er.getRecordDate()));
            else if(pbr.getBestRecordValue() < er.getRecordValue())
                recordService.updatePBR(new PersonalBestRecord(pbr.getBid(), er.getUid(), er.getEid(), er, er.getRecordValue(), er.getRecordDate()));

            return ResponseEntity.ok("update records");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/api/category")
    public ResponseEntity<String> updateCategory(@RequestHeader("Cookie") String cookieHeader,
                                                 @RequestBody CategoryDto categoryDto) {
        String authToken = getAccessToken(cookieHeader);

        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }

            if(categoryService.existCid(categoryDto.getCid())) {
                return ResponseEntity.badRequest().body("cid가 이미 존재합니다.");
            }
            if(categoryService.existCategoryName(categoryDto.getCategoryName()))
                return ResponseEntity.badRequest().body("categoryName이 이미 존재합니다.");

            categoryService.updateCategory(categoryDto);
            return ResponseEntity.ok("Category 업데이트");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/api/subcategory")
    public ResponseEntity<String> updateSubCategory(@RequestHeader("Cookie") String cookieHeader,
                                                    @RequestBody SubCategoryDto subCategoryDto) {
        String authToken = getAccessToken(cookieHeader);

        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }

            if(categoryService.existEid(subCategoryDto.getEid())) {
                return ResponseEntity.badRequest().body("eid가 이미 존재합니다.");
            }
            ExerciseCategory ec = categoryService.findByCid(subCategoryDto.getCid());
            if(ec == null)
                return ResponseEntity.badRequest().body("cid가 존재하지않습니다.");

            if(categoryService.existExerciseName(subCategoryDto.getExerciseName()))
                return ResponseEntity.badRequest().body("exerciseName이 이미 존재합니다.");

            categoryService.updateSubCategory(subCategoryDto, ec);
            return ResponseEntity.ok("SubCategory 업데이트");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/api/record")
    public List<ExerciseRecord> getRecords(@RequestParam String nickname) {
        User uid = userService.findUserByNickname(nickname);
        return recordService.findAllRecordsByUser(uid);
    }
    @GetMapping("/api/category")
    public List<ExerciseCategory> getCategories() {
        return categoryService.getAllCategory();
    }
    @GetMapping("/api/subcategory")
    public List<ExerciseSubCategory> getSubCategories(@RequestParam String cid) {
        return categoryService.getSubCategoryByCategory(cid);
    }

    @DeleteMapping("/api/record")
    public ResponseEntity<String> deleteRecords(@RequestHeader("Cookie") String cookieHeader,
                                                @RequestParam int rid) {
        String authToken = getAccessToken(cookieHeader);

        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }
            ExerciseRecord er = recordService.findRecordByRid(rid);
            if(er == null)
                return ResponseEntity.badRequest().body("record가 존재하지 않습니다.");

            if(recordService.existPBRByRecord(er)) {
                recordService.deletePBRByRecord(er);
                ExerciseRecord new_pbr = recordService.findBestRecordByUidAndEid(er);
                if(new_pbr != null)
                    recordService.updatePBR(new PersonalBestRecord(0, new_pbr.getUid(), new_pbr.getEid(), new_pbr, new_pbr.getRecordValue(), new_pbr.getRecordDate()));
            }
            recordService.deleteRecordByRid(rid);
            return ResponseEntity.ok("Record 삭제");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/api/category")
    public ResponseEntity<String> deleteCategory(@RequestHeader("Cookie") String cookieHeader,
                                                 @RequestParam String cid) {
        String authToken = getAccessToken(cookieHeader);

        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }

            if(!categoryService.existCategoryName(cid))
                return ResponseEntity.badRequest().body("Category가 존재하지 않습니다.");
            categoryService.deleteCategory(cid);
            return ResponseEntity.ok("Category 삭제");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/api/subcategory")
    public ResponseEntity<String> deleteSubCategory(@RequestHeader("Cookie") String cookieHeader,
                                                    @RequestParam String eid) {
        String authToken = getAccessToken(cookieHeader);

        try {
            Long adminId = authService.getKakaoProfile(authToken).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.badRequest().body("권한이 존재하지 않습니다.");
            }

            if(!categoryService.existEid(eid))
                return ResponseEntity.badRequest().body("SubCategory가 존재하지 않습니다.");
            categoryService.deleteSubCategory(eid);
            return ResponseEntity.ok("SubCategory 삭제");
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public String getAccessToken(String cookieHeader) {
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
}
