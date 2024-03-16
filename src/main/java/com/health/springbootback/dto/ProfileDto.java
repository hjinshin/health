package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileDto {
    private String nickname;
    private int ranking;
    private float b_sum;
}
