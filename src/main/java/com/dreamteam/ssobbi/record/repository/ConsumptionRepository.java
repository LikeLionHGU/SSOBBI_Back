package com.dreamteam.ssobbi.record.repository;

import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.entity.Record;
import com.dreamteam.ssobbi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

	ArrayList<Consumption> findByRecord(Record record);

    @Query("SELECT c FROM Consumption c WHERE c.record.user = :user AND c.record.date BETWEEN :startDate AND :endDate")
    List<Consumption> findMonthlyByUserAndDate(User user, LocalDate startDate, LocalDate endDate);
}
