package com.health.springbootback.dto;

import lombok.*;

import java.util.Date;

@Data
public class RecordsDto {
    private String categoryName;    // 카테고리명
    private String exerciseName;    // 운동명
    private float recordValue;      // 기록 값
    private Date recordDate;   // 기록일

    public RecordsDto(String categoryName, String exerciseName, float recordValue, Date recordDate) {
        this.categoryName = categoryName;
        this.exerciseName = exerciseName;
        this.recordValue = recordValue;
        this.recordDate = recordDate;
    }
}
