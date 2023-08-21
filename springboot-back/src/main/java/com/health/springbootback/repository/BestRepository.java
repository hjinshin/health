package com.health.springbootback.repository;

import com.health.springbootback.dto.BestRecordDto;
import com.health.springbootback.dto.Profile;
import com.health.springbootback.entity.PersonalBestRecord;
import com.health.springbootback.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BestRepository extends JpaRepository<PersonalBestRecord, Integer> {
    @Query("SELECT " +
            "NEW com.health.springbootback.dto.BestRecordDto(ec.categoryName, et.exerciseName, br.bestRecordValue, br.bestRecordDate) " +
            "FROM PersonalBestRecord br " +
            "INNER JOIN br.eid et " +
            "INNER JOIN et.cid ec " +
            "WHERE br.uid.uid = :uid " +
            "AND ec.cid = :cid ")
    List<BestRecordDto> findPBRByCategory(@Param("uid") Long uid, @Param("cid") CategoryType cid);


    @Query(value = "SELECT " +
            "User.nickname as nickname, rank() over (order by sum(bestRecordValue) desc) as ranking, sum(bestRecordValue) as b_sum " +
            "from PersonalBestRecord " +
            "INNER JOIN ExerciseType ON ExerciseType.eid = PersonalBestRecord.eid " +
            "INNER JOIN User ON User.uid = PersonalBestRecord.uid " +
            "WHERE cid = :cid " +
            "GROUP BY User.nickname", nativeQuery = true)
    List<Profile> findallf(@Param("cid") String cid);
}
