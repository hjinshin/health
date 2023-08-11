package com.health.springbootback.repository;

import com.health.springbootback.entity.PersonalBestRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BestRepository extends JpaRepository<PersonalBestRecord, Integer> {
}
