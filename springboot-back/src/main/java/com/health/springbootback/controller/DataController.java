package com.health.springbootback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.springbootback.dto.CategoryDto;
import com.health.springbootback.dto.MsgResponseDto;
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
    public ResponseEntity<MsgResponseDto> updateRecords(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestBody UpdateRecordDto updateRecordDto) {
        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));
            }

            User user = userService.findUserByNickname(updateRecordDto.getNickname());
            if(user == null) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, updateRecordDto.getNickname() + "가 존재하지 않습니다"));
            }

            ExerciseSubCategory et = categoryService.findERByExerciseName(updateRecordDto.getExerciseName());
            if(et == null) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, updateRecordDto.getExerciseName() + "는 존재하지 않는 운동종목입니다"));
            }

            ExerciseRecord er = new ExerciseRecord(0, user, et, updateRecordDto.getValue(), null, updateRecordDto.getLocation());
            // 기록 업데이트
            recordService.updateRecords(er);
            // 최고기록 업데이트
            PersonalBestRecord pbr = recordService.findByUidAndEid(er.getUid().getUid(), er.getEid().getEid());
            if(pbr == null)
                recordService.updatePBR(new PersonalBestRecord(0, er.getUid(), er.getEid(), er, er.getRecordValue(), er.getRecordDate()));
            else if(pbr.getBestRecordValue() < er.getRecordValue())
                recordService.updatePBR(new PersonalBestRecord(pbr.getBid(), er.getUid(), er.getEid(), er, er.getRecordValue(), er.getRecordDate()));
            return ResponseEntity.ok().body(new MsgResponseDto(true, "기록 업데이트"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
        }
    }
    @PutMapping("/api/category")
    public ResponseEntity<MsgResponseDto> updateCategory(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestBody CategoryDto categoryDto) {
        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));
            }

            if(categoryService.existCategoryByCid(categoryDto.getCid())) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, "cid가 이미 존재합니다"));
            }
            if(categoryService.existCategoryByCategoryName(categoryDto.getCategoryName()))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "categoryName이 이미 존재합니다"));

            categoryService.updateCategory(categoryDto);
            return ResponseEntity.ok().body(new MsgResponseDto(true, "Category 업데이트"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
        }
    }
    @PutMapping("/api/subcategory")
    public ResponseEntity<MsgResponseDto> updateSubCategory(@RequestHeader("Authorization") String authorizationHeader,
                                                    @RequestBody SubCategoryDto subCategoryDto) {
        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN)
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));


            if(categoryService.existSubCategoryByEid(subCategoryDto.getEid()))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "eid가 이미 존재합니다"));

            ExerciseCategory ec = categoryService.findByCid(subCategoryDto.getCid());
            if(ec == null)
                return ResponseEntity.ok().body(new MsgResponseDto(false, "cid가 존재하지않습니다"));

            if(categoryService.existSubCategoryByExerciseName(subCategoryDto.getExerciseName()))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "exerciseName이 이미 존재합니다"));

            categoryService.updateSubCategory(subCategoryDto, ec);
            return ResponseEntity.ok().body(new MsgResponseDto(true, "SubCategory 업데이트"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
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
    public ResponseEntity<MsgResponseDto> deleteRecords(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestParam int rid) {
        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN) {
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));
            }
            ExerciseRecord er = recordService.findRecordByRid(rid);
            if(er == null)
                return ResponseEntity.ok().body(new MsgResponseDto(false, "record가 존재하지 않습니다"));

            if(recordService.existPBRByRecord(er)) {
                recordService.deletePBRByRecord(er);
                ExerciseRecord new_pbr = recordService.findBestRecordByUidAndEid(er);
                if(new_pbr != null)
                    recordService.updatePBR(new PersonalBestRecord(0, new_pbr.getUid(), new_pbr.getEid(), new_pbr, new_pbr.getRecordValue(), new_pbr.getRecordDate()));
            }
            recordService.deleteRecordByRid(rid);
            return ResponseEntity.ok().body(new MsgResponseDto(true, "Record 삭제"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
        }
    }
    @DeleteMapping("/api/category")
    public ResponseEntity<MsgResponseDto> deleteCategory(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestParam String cid) {
        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN)
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));

            if(!categoryService.existCategoryByCid(cid))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "Category가 존재하지 않습니다"));
            if(categoryService.existSubCategoryByCid(cid))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "Category 내에 SubCategory가 존재합니다"));
            categoryService.deleteCategory(cid);
            return ResponseEntity.ok().body(new MsgResponseDto(true, "Category 삭제"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
        }
    }
    @DeleteMapping("/api/subcategory")
    public ResponseEntity<MsgResponseDto> deleteSubCategory(@RequestHeader("Authorization") String authorizationHeader,
                                                    @RequestParam String eid) {

        try {
            String token = authorizationHeader.split(" ")[1];
            Long adminId = authService.getKakaoProfile(token).getUid();
            User admin = userService.findMember(adminId);
            if(admin.getRole() != RoleType.ADMIN)
                return ResponseEntity.ok().body(new MsgResponseDto(false, "권한이 존재하지 않습니다"));

            if(!categoryService.existSubCategoryByEid(eid))
                return ResponseEntity.ok().body(new MsgResponseDto(false, "SubCategory가 존재하지 않습니다"));
            categoryService.deleteSubCategory(eid);
            return ResponseEntity.ok().body(new MsgResponseDto(true, "SubCategory 삭제"));
        } catch(HttpStatusCodeException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new MsgResponseDto(false, e.getMessage()));
        }
    }

}
