package com.health.springbootback.repository;

import com.health.springbootback.dto.RecordsDto;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface RecordRepository extends JpaRepository<ExerciseRecord, Integer> {
    @Query("SELECT " +
            "NEW com.health.springbootback.dto.RecordsDto(ec.categoryName, et.exerciseName, er.recordValue, er.recordDate) " +
            "FROM ExerciseRecord er " +
            "INNER JOIN er.eid et " +
            "INNER JOIN et.cid ec " +
            "WHERE er.uid.uid = :uid")
    List<RecordsDto> findByUid(@Param("uid") Long uid);

    @Query("SELECT " +
            "NEW com.health.springbootback.dto.RecordsDto(ec.categoryName, et.exerciseName, er.recordValue, er.recordDate) " +
            "FROM ExerciseRecord er " +
            "INNER JOIN er.eid et " +
            "INNER JOIN et.cid ec " +
            "WHERE er.uid.uid = :uid " +
            "AND ec.cid = :cid")
    List<RecordsDto> findByUidAndCid(@Param("uid") Long uid,  @Param("cid") CategoryType cid);
}
