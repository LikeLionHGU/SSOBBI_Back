package com.dreamteam.ssobbi.record.repository;

import com.dreamteam.ssobbi.record.entity.Consumption;
import com.dreamteam.ssobbi.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

	ArrayList<Consumption> findByRecord(Record record);
}
