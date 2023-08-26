package com.health.springbootback.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
    private String nickname;
    private float b_sum;
    private String most;
}
