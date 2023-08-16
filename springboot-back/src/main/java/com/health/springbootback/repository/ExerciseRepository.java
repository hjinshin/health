package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseType;
import com.health.springbootback.enums.CategoryType;
import com.health.springbootback.enums.SubCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<ExerciseType, SubCategoryType> {

}
