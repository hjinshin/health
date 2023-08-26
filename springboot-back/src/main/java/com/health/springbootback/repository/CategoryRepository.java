package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ExerciseCategory, String> {
    boolean existsByCategoryName(String categoryName);
}
