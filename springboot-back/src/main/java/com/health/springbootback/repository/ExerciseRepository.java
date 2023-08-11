package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseType;
import com.health.springbootback.enums.SubCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseType, SubCategoryType> {

}
