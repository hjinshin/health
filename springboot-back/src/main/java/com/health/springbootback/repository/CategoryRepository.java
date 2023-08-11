package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseCategory;
import com.health.springbootback.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ExerciseCategory, CategoryType> {

}
