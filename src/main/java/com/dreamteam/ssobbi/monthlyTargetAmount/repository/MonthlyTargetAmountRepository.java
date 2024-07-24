package com.dreamteam.ssobbi.monthlyTargetAmount.repository;

import com.dreamteam.ssobbi.monthlyTargetAmount.entity.MonthlyTargetAmount;
import com.dreamteam.ssobbi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface MonthlyTargetAmountRepository extends JpaRepository<MonthlyTargetAmount, Long> {

	Optional<Object> findByUserAndCategory(User user, String category);

	ArrayList<MonthlyTargetAmount> findByUser(User user);
}
