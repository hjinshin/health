package com.health.springbootback.controller;

import com.health.springbootback.model.SearchBase;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SearchController {

    @GetMapping("/api/search")
    public SearchBase search(@RequestParam String userNm) {
        System.out.println(userNm);

        return new SearchBase(userNm);
    }
}
