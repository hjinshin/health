package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRecordDto {
    private String nickname;
    private String exerciseName;
    private float value;
}
