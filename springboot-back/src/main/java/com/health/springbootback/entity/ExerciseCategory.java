package com.health.springbootback.entity;

import com.health.springbootback.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseCategory {
    @Id
    @Enumerated(EnumType.STRING)
    private CategoryType cid;    // 카테고리 식별자

    @Column(nullable = false, length = 20)
    private String categoryName;    // 카테고리 이름
}
