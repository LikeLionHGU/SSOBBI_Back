package com.dreamteam.ssobbi.record.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import com.dreamteam.ssobbi.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Record extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "record_id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private Integer happinessRate;

  @Column(columnDefinition = "varchar(250)")
  private String content;

  @Column(nullable = false)
  private LocalDate date;

  @OneToMany(
      mappedBy = "record",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Consumption> consumptions;

  public static Record from(User user, RecordDto dto) {
    Record record =
        Record.builder()
            .user(user)
            .happinessRate(dto.getHappinessRate())
            .content(dto.getContent())
            .date(dto.getDate())
            .build();
    record.setConsumptions(Consumption.listFrom(dto.getConsumptions(), record));
    return record;
  }

  public void update(RecordDto dto) {
    this.happinessRate = dto.getHappinessRate();
    this.content = dto.getContent();
    this.consumptions.clear();
    this.consumptions.addAll(Consumption.listFrom(dto.getConsumptions(), this));
  }
}
