package com.health.springbootback.dto;

import com.health.springbootback.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDto {
    public String nickname;
    public RoleType role;
}
