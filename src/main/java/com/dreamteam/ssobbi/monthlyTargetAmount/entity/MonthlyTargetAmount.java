package com.dreamteam.ssobbi.monthlyTargetAmount.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.monthlyTargetAmount.dto.MonthlyTargetAmountDto;
import com.dreamteam.ssobbi.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTargetAmount extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthly_target_amount_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false, columnDefinition = "varchar(8)")
    private String category;

    public static MonthlyTargetAmount from(MonthlyTargetAmountDto dto, User user) {
        return MonthlyTargetAmount.builder()
                .user(user)
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .build();
    }
}
