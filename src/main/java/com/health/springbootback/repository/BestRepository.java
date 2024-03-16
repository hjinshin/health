package com.health.springbootback.repository;

import com.health.springbootback.dto.BestRecordDto;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.model.Profile;
import com.health.springbootback.entity.PersonalBestRecord;
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
    List<BestRecordDto> findPBRByCategory(@Param("uid") Long uid, @Param("cid") String cid);

    @Query(value = "SELECT " +
            "    subquery.nickname, " +
            "    SUM(subquery.bestrecordvalue) AS b_sum, " +
            "    ( " +
            "        SELECT et_sub.exercisename " +
            "        FROM PersonalBestRecord br_sub " +
            "        INNER JOIN ExerciseSubCategory et_sub ON br_sub.eid = et_sub.eid " +
            "        WHERE et_sub.cid = :cid AND br_sub.uid = subquery.uid " +
            "        ORDER BY br_sub.bestrecordvalue DESC LIMIT 1 " +
            "    ) AS most " +
            "FROM (" +
            "    SELECT " +
            "        u.nickname, " +
            "        br.bestrecordvalue, " +
            "        br.uid " +
            "    FROM PersonalBestRecord br " +
            "    INNER JOIN ExerciseSubCategory et ON et.eid = br.eid " +
            "    INNER JOIN User u ON u.uid = br.uid " +
            "    WHERE et.cid = :cid " +
            "    ORDER BY et.eid " +
            ") AS subquery " +
            "GROUP BY subquery.nickname, subquery.uid " +
            "ORDER BY b_sum DESC", nativeQuery = true)
    List<UserList> findByCategory(@Param("cid")String cid);

    @Query(value = "SELECT " +
            "    subquery.nickname, " +
            "    SUM(subquery.bestrecordvalue) AS b_sum, " +
            "    ( " +
            "        SELECT et_sub.exercisename " +
            "        FROM PersonalBestRecord br_sub " +
            "        INNER JOIN ExerciseSubCategory et_sub ON br_sub.eid = et_sub.eid " +
            "        WHERE et_sub.cid = :cid AND br_sub.uid = subquery.uid " +
            "        ORDER BY br_sub.bestrecordvalue DESC LIMIT 1 " +
            "    ) AS most " +
            "FROM (" +
            "    SELECT " +
            "        u.nickname, " +
            "        br.bestrecordvalue, " +
            "        br.uid " +
            "    FROM PersonalBestRecord br " +
            "    INNER JOIN ExerciseSubCategory et ON et.eid = br.eid " +
            "    INNER JOIN User u ON u.uid = br.uid " +
            "    WHERE et.cid = :cid AND et.eid = :eid " +
            "    ORDER BY et.eid " +
            ") AS subquery " +
            "GROUP BY subquery.nickname, subquery.uid " +
            "ORDER BY b_sum DESC", nativeQuery = true)
    List<UserList> findByCategoryAndSubC(@Param("cid")String cid,@Param("eid") String eid);

    @Query(value = "SELECT " +
            "User.nickname as nickname, rank() over (order by sum(bestRecordValue) desc) as ranking, sum(bestRecordValue) as b_sum " +
            "from PersonalBestRecord " +
            "INNER JOIN ExerciseSubCategory ON ExerciseSubCategory.eid = PersonalBestRecord.eid " +
            "INNER JOIN User ON User.uid = PersonalBestRecord.uid " +
            "WHERE cid = :cid " +
            "GROUP BY User.uid", nativeQuery = true)
    List<Profile> findRankingByCid(@Param("cid") String cid);

    PersonalBestRecord findByUid_UidAndEid_Eid(@Param("uid") Long uid, @Param("eid") String eid);

    boolean existsByRid(ExerciseRecord rid);

    void deleteByRid(ExerciseRecord rid);
}
