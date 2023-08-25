package com.health.springbootback.service;

import com.health.springbootback.dto.BestRecordDto;
import com.health.springbootback.entity.ExerciseRecord;
import com.health.springbootback.entity.PersonalBestRecord;
import com.health.springbootback.model.Profile;
import com.health.springbootback.dto.ProfileDto;
import com.health.springbootback.dto.RecordsDto;
import com.health.springbootback.enums.CategoryType;
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
        PersonalBestRecord pbr = bestRepository.findByUid_UidAndEid_Eid(er.getUid().getUid(), er.getEid().getEid());
        if(pbr == null)
            bestRepository.save(new PersonalBestRecord(0, er.getUid(), er.getEid(), er.getRecordValue(), er.getRecordDate()));
        else if(pbr.getBestRecordValue() < er.getRecordValue())
            bestRepository.save(new PersonalBestRecord(pbr.getBid(), er.getUid(), er.getEid(), er.getRecordValue(), er.getRecordDate()));

    }

    @Transactional
    public List<RecordsDto> findRecords(Long uid, String category) {
        //  category가 whole이면
        if (Objects.equals(category, "whole"))
            return recordRepository.findByUid(uid);
            // category가 4major이면
        else if (Objects.equals(category, "4major"))
            return recordRepository.findByUidAndCid(uid, CategoryType.FOURMAJOR);
            //  category가 free-style이면
        else if (Objects.equals(category, "free-style"))
            return recordRepository.findByUidAndCid(uid, CategoryType.FREESTYLE);
            //  category가 bare-body이면
        else if (Objects.equals(category, "bare-body"))
            return recordRepository.findByUidAndCid(uid, CategoryType.BAREBODY);

        return null;
    }

    @Transactional
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
        System.out.println(profileDto);
        return profileDto;
    }

    @Transactional
    public List<BestRecordDto> findPBR(Long uid, String category) {
        CategoryType c = null;

        if (Objects.equals(category, "4major"))
            c = CategoryType.FOURMAJOR;
            //  category가 free-style이면
        else if (Objects.equals(category, "free-style"))
            c = CategoryType.FREESTYLE;
            //  category가 bare-body이면
        else if (Objects.equals(category, "bare-body"))
            c = CategoryType.BAREBODY;

        return bestRepository.findPBRByCategory(uid, c);
    }

}
