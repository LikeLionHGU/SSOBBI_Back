package com.dreamteam.ssobbi.record.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.user.entity.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Record extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "varchar(250)")
    private String content;

    @Column(nullable = false)
    private Date date;
}
