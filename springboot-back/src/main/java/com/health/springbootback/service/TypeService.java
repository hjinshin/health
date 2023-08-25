package com.health.springbootback.service;

import com.health.springbootback.entity.ExerciseType;
import com.health.springbootback.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypeService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public ExerciseType findERByExerciseName(String exerciseName) {
        return exerciseRepository.findByExerciseName(exerciseName);
    }
}
