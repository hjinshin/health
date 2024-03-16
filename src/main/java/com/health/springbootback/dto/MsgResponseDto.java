package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgResponseDto {
    private boolean success;
    private String message;
}
