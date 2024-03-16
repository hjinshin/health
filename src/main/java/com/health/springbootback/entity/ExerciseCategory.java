package com.health.springbootback.entity;

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
    private String cid;    // 카테고리 식별자

    @Column(nullable = false, length = 20)
    private String categoryName;    // 카테고리 이름
}
