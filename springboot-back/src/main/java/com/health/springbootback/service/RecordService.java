package com.health.springbootback.service;

import com.health.springbootback.dto.RecordsDto;
import com.health.springbootback.entity.ExerciseCategory;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.entity.ExerciseType;
import com.health.springbootback.repository.CategoryRepository;
import com.health.springbootback.repository.ExerciseRepository;
import com.health.springbootback.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<RecordsDto> findAllRecords(Long uid) {
        List<RecordsDto> res = new ArrayList<>();
        List<ExerciseRecord> lists = recordRepository.findByUid_Uid(uid);

        for(ExerciseRecord list: lists) {
            RecordsDto dto = new RecordsDto();
            ExerciseType et = exerciseRepository.findById(list.getEid().getEid()).get();
            ExerciseCategory ct = categoryRepository.findById(et.getCid().getCid()).get();
            dto.setCategoryName(ct.getCategoryName());
            dto.setExerciseName(et.getExerciseName());
            dto.setRecordValue(list.getRecordValue());
            dto.setDate(list.getRecordDate());

            res.add(dto);
        }

        return res;
    }
}
