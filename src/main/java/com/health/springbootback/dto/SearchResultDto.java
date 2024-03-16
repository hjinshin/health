package com.health.springbootback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResultDto {
    public boolean searchSuccess;
    public ProfileDto profileDto;
    public List<RecordsDto> recordsDtoList;
    public List<BestRecordDto> bestRecordDtoList;
}
