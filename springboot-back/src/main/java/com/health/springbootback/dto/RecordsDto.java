package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecordsDto {
    private String categoryName;    // 카테고리명
    private String exerciseName;    // 운동명
    private float recordValue;      // 기록 값
    private Timestamp date;         // 기록일
}
