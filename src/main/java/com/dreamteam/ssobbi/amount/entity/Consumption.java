package com.dreamteam.ssobbi.amount.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.record.entity.Record;
import jakarta.persistence.*;

@Entity
public class Consumption extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumption_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    private Integer amount;

    private Boolean isOverConsumption;

    private Integer targetAmount;

    private String category;
}
