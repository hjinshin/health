package com.health.springbootback.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class searchController {
    @GetMapping("/api/search/{userName}")
    public String search(@PathVariable String userName) {
        System.out.println(userName);
        return userName;
    }
}
