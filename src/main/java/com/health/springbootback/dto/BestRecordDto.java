package com.health.springbootback.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BestRecordDto {
    private String categoryName;      // 카테고리명
    private String exerciseName;      // 운동명
    private float bestRecordValue;    // 최고기록 값
    private Date bestRecordDate; // 기록일

    public BestRecordDto(String categoryName, String exerciseName, float bestRecordValue, Date bestRecordDate) {
        this.categoryName = categoryName;
        this.exerciseName = exerciseName;
        this.bestRecordValue = bestRecordValue;
        this.bestRecordDate = bestRecordDate;
    }
}