package com.health.springbootback.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private String nickname;
    private int ranking;
    private float b_sum;

    public ProfileDto(String nickname, int ranking, float b_sum) {
        this.nickname = nickname;
        this.ranking = ranking;
        this.b_sum = b_sum;
    }

}
