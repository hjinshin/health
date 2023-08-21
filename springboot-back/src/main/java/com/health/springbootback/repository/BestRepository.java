package com.health.springbootback.repository;

import com.health.springbootback.dto.BestRecordDto;
import com.health.springbootback.model.Profile;
import com.health.springbootback.entity.PersonalBestRecord;
import com.health.springbootback.enums.CategoryType;
import com.health.springbootback.model.UserList;
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
            "u.nickname as nickname, SUM(br.bestRecordValue) AS b_sum, " +
            "(" +
            "   SELECT et_sub.exerciseName " +
            "   FROM personalbestrecord br_sub " +
            "   INNER JOIN exercisetype et_sub ON br_sub.eid = et_sub.eid " +
            "   WHERE et_sub.cid = :cid" +
            "   ORDER BY br_sub.bestRecordValue DESC LIMIT 1" +
            ") as most " +
            "FROM PersonalBestRecord br " +
            "INNER JOIN ExerciseType et ON et.eid = br.eid " +
            "INNER JOIN User u ON u.uid = br.uid " +
            "WHERE et.cid = :cid " +
            "GROUP BY u.nickname " +
            "ORDER BY b_sum DESC", nativeQuery = true)
    List<UserList> findByCategory(String cid);

    @Query(value = "SELECT " +
            "u.nickname as nickname, SUM(br.bestRecordValue) AS b_sum, " +
            "(" +
            "   SELECT et_sub.exerciseName " +
            "   FROM personalbestrecord br_sub " +
            "   INNER JOIN exercisetype et_sub ON br_sub.eid = et_sub.eid " +
            "   WHERE et_sub.cid = :cid AND et_sub.eid = :eid " +
            "   ORDER BY br_sub.bestRecordValue DESC LIMIT 1" +
            ") as most " +
            "FROM PersonalBestRecord br " +
            "INNER JOIN ExerciseType et ON et.eid = br.eid " +
            "INNER JOIN User u ON u.uid = br.uid " +
            "WHERE et.cid = :cid AND et.eid = :eid " +
            "GROUP BY u.nickname " +
            "ORDER BY b_sum DESC", nativeQuery = true)
    List<UserList> findByCategoryAndSubC(String cid, String eid);

    @Query(value = "SELECT " +
            "User.nickname as nickname, rank() over (order by sum(bestRecordValue) desc) as ranking, sum(bestRecordValue) as b_sum " +
            "from PersonalBestRecord " +
            "INNER JOIN ExerciseType ON ExerciseType.eid = PersonalBestRecord.eid " +
            "INNER JOIN User ON User.uid = PersonalBestRecord.uid " +
            "WHERE cid = :cid " +
            "GROUP BY User.uid", nativeQuery = true)

    List<Profile> findallf(@Param("cid") String cid);
}
