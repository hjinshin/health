package com.health.springbootback.service;

import com.health.springbootback.dto.BestRecordDto;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.entity.PersonalBestRecord;
import com.health.springbootback.entity.User;
import com.health.springbootback.model.Profile;
import com.health.springbootback.dto.ProfileDto;
import com.health.springbootback.dto.RecordsDto;
import com.health.springbootback.repository.BestRepository;
import com.health.springbootback.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BestRepository bestRepository;

    @Transactional
    public void updateRecords(ExerciseRecord er) {
        recordRepository.save(er);
    }
    @Transactional
    public void updatePBR(PersonalBestRecord pbr) {
        bestRepository.save(pbr);
    }

    @Transactional(readOnly = true)
    public PersonalBestRecord findByUidAndEid(Long uid, String eid) {
        return bestRepository.findByUid_UidAndEid_Eid(uid, eid);
    }
    @Transactional(readOnly = true)
    public ExerciseRecord findRecordByRid(int rid) {
        return recordRepository.findById(rid).orElse(null);
    }
    @Transactional(readOnly = true)
    public List<ExerciseRecord> findAllRecordsByUser(User uid) {
        return recordRepository.findByUid(uid);
    }

    @Transactional(readOnly = true)
    public List<RecordsDto> findRecords(Long uid, String category) {
        //  category가 whole이면
        if (Objects.equals(category, "whole"))
            return recordRepository.findRecordsDtoByUid(uid);
            // category가 4major이면
        else if (Objects.equals(category, "4major"))
            return recordRepository.findByUidAndCid(uid, "FOURMAJOR");
            //  category가 free-style이면
        else if (Objects.equals(category, "free-style"))
            return recordRepository.findByUidAndCid(uid, "FREESTYLE");
            //  category가 bare-body이면
        else if (Objects.equals(category, "bare-body"))
            return recordRepository.findByUidAndCid(uid, "BAREBODY");

        return null;
    }

    @Transactional(readOnly = true)
    public ProfileDto findRanking(String userNm) {
        List<Profile> profileList = bestRepository.findRankingByCid("FOURMAJOR");
        ProfileDto profileDto = profileList.stream()
                .filter(profile -> profile.getNickname().equals(userNm))
                .map(profile -> {
            String nickname = profile.getNickname();
            int ranking = profile.getRanking();
            float b_sum = profile.getB_sum();

            return new ProfileDto(nickname, ranking, b_sum);
        }).findFirst().orElse(null);

        if(profileDto == null)
            return new ProfileDto(userNm, -1, 0);
        return profileDto;
    }

    @Transactional(readOnly = true)
    public List<BestRecordDto> findPBR(Long uid, String category) {
        String c = null;

        if (Objects.equals(category, "4major"))
            c = "FOURMAJOR";
            //  category가 free-style이면
        else if (Objects.equals(category, "free-style"))
            c = "FREESTYLE";
            //  category가 bare-body이면
        else if (Objects.equals(category, "bare-body"))
            c = "BAREBODY";

        return bestRepository.findPBRByCategory(uid, c);
    }
    @Transactional(readOnly = true)
    public ExerciseRecord findBestRecordByUidAndEid(ExerciseRecord er) {
        List<ExerciseRecord> exerciseRecordList = recordRepository.findByUidAndEidOrderByRecordValueDesc(er.getUid(), er.getEid());
        if(exerciseRecordList.size() > 2)
            return exerciseRecordList.get(1);
        else
            return null;
    }

    @Transactional(readOnly = true)
    public boolean existPBRByRecord(ExerciseRecord er) {
        return bestRepository.existsByRid(er);
    }

    @Transactional(readOnly = true)
    public boolean exeistRecordBySubCategory(String eid) {
        return recordRepository.existsByEid_Eid(eid);
    }

    @Transactional
    public void deleteRecordByRid(int rid) {
        recordRepository.deleteById(rid);
    }
    @Transactional
    public void deletePBRByRecord(ExerciseRecord er) {
        bestRepository.deleteByRid(er);
    }
}
