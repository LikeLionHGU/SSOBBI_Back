package com.dreamteam.ssobbi.monthlyTargetAmount.repository;

import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyTargetAmountRepository extends JpaRepository<MonthlyTargetAmount, Long> {

}
