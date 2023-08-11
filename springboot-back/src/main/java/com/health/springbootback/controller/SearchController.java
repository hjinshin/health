package com.health.springbootback.controller;

import com.health.springbootback.dto.RecordsDto;
import com.health.springbootback.dto.SearchResultDto;
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
    public SearchResultDto search(@RequestParam String userNm) {
        System.out.println(userNm);

        Long uid = userService.findUidByNickname(userNm);
        if(uid == -1) {
            return new SearchResultDto(false, userNm, null);
        }
        System.out.println(uid);
        List<RecordsDto> recordsDtos = recordService.findAllRecords(uid);

        // 종목별 최고기록

        return new SearchResultDto(true, userNm, recordsDtos);
    }
}
