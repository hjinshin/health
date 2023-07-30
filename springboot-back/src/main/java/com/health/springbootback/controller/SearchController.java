package com.health.springbootback.controller;

import com.health.springbootback.model.SearchBase;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SearchController {

    @GetMapping("/api/search")
    public SearchBase search(@RequestParam String query) {
        System.out.println(query);

        String userNm = query;
        return new SearchBase(userNm);
    }
}
