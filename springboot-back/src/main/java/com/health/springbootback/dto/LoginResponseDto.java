package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    public boolean loginSuccess;
    public UserInfoDto userInfo;

}