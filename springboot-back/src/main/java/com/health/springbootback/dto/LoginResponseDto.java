package com.health.springbootback.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    public boolean loginSuccess;
    public UserInfoDto userInfo;

}