package com.health.springbootback.entity;

import com.health.springbootback.enums.SubCategoryType;
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
public class ExerciseType {
    @Id
    @Enumerated(EnumType.STRING)
    private SubCategoryType eid; // 운동 종목 식별자

    @ManyToOne
    @JoinColumn(name="cid", referencedColumnName = "cid")
    @Enumerated(EnumType.STRING)
    private ExerciseCategory cid;    // 카테고리 식별자(외래키)

    @Column(nullable = false, length = 20)
    private String exerciseName;        // 운동명
}
