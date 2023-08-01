package com.health.springbootback.controller;

import com.health.springbootback.model.UserList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class RankingController {

    @GetMapping("/api/user-ranking")
    public List<UserList> userRanking(@RequestParam String category, String subcategory) {
        List<UserList> res = new ArrayList<>();
        System.out.println(category);
        if(category.equals("4-major")) {
            res.add(new UserList("신형진", 1));
            res.add(new UserList("오창호", 2));
            res.add(new UserList("차준규", 3));
        } else if(category.equals("freestyle")) {
            res.add(new UserList("오창호", 1));
            res.add(new UserList("차준규", 2));
            res.add(new UserList("신형진", 3));
        } else {
            res.add(new UserList("차준규", 1));
            res.add(new UserList("신형진", 2));
            res.add(new UserList("오창호", 3));
        }
        return res;
    }
}
