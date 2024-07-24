package com.dreamteam.ssobbi.record.repository;

import com.dreamteam.ssobbi.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface RecordRepository extends JpaRepository<Record,Long> {
    @Query("select case when count(r) > 0 then true else false end from Record r where r.user.id = :userId and r.date = :date")
    boolean existsByUser_IdAndDate(Long userId, LocalDate date);
}
