package com.health.springbootback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalBestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;     // 개인 최고기록 식별자

    @ManyToOne
    @JoinColumn(name="uid", referencedColumnName = "uid")
    private User uid;   // 회원ID(외래키)

    @ManyToOne
    @JoinColumn(name="eid", referencedColumnName = "eid")
    private ExerciseType eid;   // 운동 종목 식별자(외래키)

    @Column(nullable = false)
    private float bestRecordValue;  // 기록 값

    @CreationTimestamp
    private Timestamp bestRecordDate; // 기록일
}
