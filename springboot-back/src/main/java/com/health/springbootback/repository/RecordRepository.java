package com.health.springbootback.repository;

import com.health.springbootback.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecordRepository extends JpaRepository<ExerciseRecord, Integer> {
    List<ExerciseRecord> findByUid_Uid(Long uid_uid);
}
