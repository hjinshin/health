package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<ExerciseSubCategory, String> {

    ExerciseSubCategory findByExerciseName(String exerciseName);

    boolean existsByExerciseName(String exerciseName);

    List<ExerciseSubCategory> findByCid_Cid(String cid);

    boolean existsByCid_Cid(String cid);
}
