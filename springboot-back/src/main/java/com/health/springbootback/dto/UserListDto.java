package com.health.springbootback.dto;

import com.health.springbootback.enums.SubCategoryType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {
    private String nickname;
    private float b_sum;
    private String most;
}
