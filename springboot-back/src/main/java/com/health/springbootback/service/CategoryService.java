package com.health.springbootback.service;

import com.health.springbootback.dto.CategoryDto;
import com.health.springbootback.dto.SubCategoryDto;
import com.health.springbootback.entity.ExerciseCategory;
import com.health.springbootback.entity.ExerciseSubCategory;
import com.health.springbootback.repository.CategoryRepository;
import com.health.springbootback.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ExerciseSubCategory findERByExerciseName(String exerciseName) {
        return subCategoryRepository.findByExerciseName(exerciseName);
    }

    @Transactional(readOnly = true)
    public ExerciseCategory findByCid(String cid) {
        return categoryRepository.findById(cid).orElse(null);
    }
    @Transactional
    public void updateCategory(CategoryDto categoryDto) {
        categoryRepository.save(new ExerciseCategory(categoryDto.getCid(), categoryDto.getCategoryName()));
    }
    @Transactional
    public void updateSubCategory(SubCategoryDto subCategoryDto, ExerciseCategory ec) {
        subCategoryRepository.save(new ExerciseSubCategory(subCategoryDto.getEid(), ec, subCategoryDto.getExerciseName()));
    }

    @Transactional(readOnly = true)
    public boolean existCid(String cid) {
        return categoryRepository.existsById(cid);
    }

    @Transactional(readOnly = true)
    public boolean existCategoryName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }

    @Transactional(readOnly = true)
    public boolean existEid(String eid) {
        return subCategoryRepository.existsById(eid);
    }

    @Transactional(readOnly = true)
    public boolean existExerciseName(String exerciseName) {
        return subCategoryRepository.existsByExerciseName(exerciseName);
    }

    @Transactional(readOnly = true)
    public List<ExerciseCategory> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ExerciseSubCategory> getSubCategoryByCategory(String cid) {
        return subCategoryRepository.findByCid_Cid(cid);
    }
    @Transactional
    public void deleteCategory(String cid) {
        categoryRepository.deleteById(cid);
    }
    @Transactional
    public void deleteSubCategory(String eid) {
        subCategoryRepository.deleteById(eid);
    }
}
