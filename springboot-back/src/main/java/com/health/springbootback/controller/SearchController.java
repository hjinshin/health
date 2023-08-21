package com.health.springbootback.controller;

import com.health.springbootback.dto.*;
import com.health.springbootback.service.RecordService;
import com.health.springbootback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SearchController {
    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public SearchController(UserService userService, RecordService recordService) {
        this.userService = userService;
        this.recordService = recordService;
    }

    @GetMapping("/api/search")
    public SearchResultDto search(/*@RequestParam String category,*/ String userNm) {
        System.out.println(userNm);
        String category1 = "whole";
        String category2 = "4major";

        Long uid = userService.findUidByNickname(userNm);
        List<RecordsDto> recordsDtos;
        List<BestRecordDto> bestRecordDtos;

        if(uid == -1)   return new SearchResultDto(false, null, null, null);
        System.out.println(uid);

        // 프로필(nickname, ranking, 4대 합)
        ProfileDto profileDto = recordService.findRanking(userNm);
        System.out.println(profileDto);
        // 카테고리별 recordDto
        recordsDtos = recordService.findRecords(uid, category1);
        // 종목별 최고기록
        bestRecordDtos = recordService.findPBR(uid, category2);

        return new SearchResultDto(true, profileDto, recordsDtos, bestRecordDtos);
    }

    @GetMapping("/api/search/records")
    public SearchResultDto searchRecords(@RequestParam String category, String userNm) {
        String category1 = category;

        Long uid = userService.findUidByNickname(userNm);
        List<RecordsDto> recordsDtos;

        if(uid == -1) return new SearchResultDto(false, null, null, null);

        recordsDtos = recordService.findRecords(uid, category1);
        return new SearchResultDto(true, null, recordsDtos, null);
    }

    @GetMapping("/api/search/pbr")
    public SearchResultDto searchPersonalBestRecord(@RequestParam String category,String userNm) {
        String category2 = category;

        Long uid = userService.findUidByNickname(userNm);
        List<BestRecordDto> bestRecordDtos;

        if(uid == -1)   return new SearchResultDto(false, null, null, null);

        bestRecordDtos = recordService.findPBR(uid, category2);
        return new SearchResultDto(true, null, null, bestRecordDtos);
    }
}
