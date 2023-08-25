package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateResponseDto {
    public boolean updateSuccess;
    public String message;
}
