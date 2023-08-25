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
public class ExerciseSubCategory {
    @Id
    private String eid; // 운동 종목 식별자

    @ManyToOne
    @JoinColumn(name="cid", referencedColumnName = "cid")
    private ExerciseCategory cid;    // 카테고리 식별자(외래키)

    @Column(nullable = false, length = 20)
    private String exerciseName;        // 운동명
}
