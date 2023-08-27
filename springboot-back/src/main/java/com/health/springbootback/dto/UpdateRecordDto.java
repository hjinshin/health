package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecordDto {
    private String nickname;
    private String exerciseName;
    private float value;
}
