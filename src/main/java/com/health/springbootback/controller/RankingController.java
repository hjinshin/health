package com.health.springbootback.controller;

import com.health.springbootback.dto.UserListDto;
import com.health.springbootback.model.FitnessList;
import com.health.springbootback.service.RankingService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin
@RestController
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/api/user-ranking")
    public List<UserListDto> userRanking(@RequestParam String category, String subcategory) {
        System.out.println(category + ", " + subcategory);
        return rankingService.findUserRanking(category, subcategory);
    }

    @GetMapping("/api/fitness-ranking")
    public List<FitnessList> fitnessRanking(@RequestParam String category, String subcategory) {
        List<FitnessList> res = new ArrayList<>();
        System.out.println(category);
        if(category.equals("chest")) {
            res.add(new FitnessList("가슴1", 1));
            res.add(new FitnessList("가슴2", 2));
            res.add(new FitnessList("가슴3", 3));
        } else if(category.equals("back")) {
            res.add(new FitnessList("등1", 1));
            res.add(new FitnessList("등2", 2));
            res.add(new FitnessList("등3", 3));
        } else if(category.equals("lower")){
            res.add(new FitnessList("하체1", 1));
            res.add(new FitnessList("하체2", 2));
            res.add(new FitnessList("하체3", 3));
        } else if(category.equals("shoulder")) {
            res.add(new FitnessList("어깨1", 1));
            res.add(new FitnessList("어깨2", 2));
            res.add(new FitnessList("어깨3", 3));
        } else{
            res.add(new FitnessList("복부1", 1));
            res.add(new FitnessList("복부2", 2));
            res.add(new FitnessList("복부3", 3));
        }
        return res;
    }
}
