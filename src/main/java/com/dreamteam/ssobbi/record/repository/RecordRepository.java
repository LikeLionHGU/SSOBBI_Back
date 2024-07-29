package com.dreamteam.ssobbi.record.repository;

import com.dreamteam.ssobbi.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record,Long> {
    @Query("select case when count(r) > 0 then true else false end from Record r where r.user.id = :userId and r.date = :date")
    boolean existsByUser_IdAndDate(Long userId, LocalDate date);

    @Query("select r from Record r join fetch r.consumptions where r.date = :date and r.user.id = :userId")
    Optional<Record> findByDateAndUserWithConsumption(LocalDate date, Long userId);

    @Query("select r from Record r join fetch r.consumptions where r.date between :startDate and :endDate and r.user.id = :userId")
    List<Record> findByDateBetweenAndUserWithConsumptions(LocalDate startDate, LocalDate endDate, Long userId);
}
