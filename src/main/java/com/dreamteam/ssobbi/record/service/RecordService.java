package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.base.exception.AlreadyExistException;
import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.base.exception.UnauthorizedAccessException;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import com.dreamteam.ssobbi.record.entity.Record;
import com.dreamteam.ssobbi.record.repository.RecordRepository;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
  private final RecordRepository recordRepository;
  private final UserRepository userRepository;

  public void addRecord(RecordDto dto, Long userId) {
    if (recordRepository.existsByUser_IdAndDate(userId, dto.getDate())) {
      throw new AlreadyExistException("이미 해당 날짜의 기록이 존재합니다.");
    }
    recordRepository.save(
        Record.from(
            userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다.")),
            dto));
  }

  public RecordDto getDailyRecord(LocalDate date, Long userId) {
    Optional<Record> record = recordRepository.findByDateAndUserWithConsumption(date, userId);
    return record.map(RecordDto::withConsumptions).orElse(null);
  }

  public void updateRecord(Long id, RecordDto dto, Long userId) {
    Record record =
        recordRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 기록이 존재하지 않습니다."));
    if (!record.getUser().getId().equals(userId)) {
      throw new UnauthorizedAccessException();
    }
    record.update(dto);
  }

  public void deleteRecord(Long id, Long userId) {
    Record record =
        recordRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 기록이 존재하지 않습니다."));
    if (!record.getUser().getId().equals(userId)) {
      throw new UnauthorizedAccessException();
    }
    recordRepository.delete(record);
  }

  public List<RecordDto> getMonthlyRecordWithConsumptions(LocalDate date, Long userId) {
    return recordRepository
        .findByDateBetweenAndUserWithConsumptions(
            date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()), userId)
        .stream()
        .map(RecordDto::withConsumptions)
        .collect(Collectors.toList());
  }

  public List<RecordDto> getWeeklyRecordWithConsumptions(LocalDate date, Long userId) { // 일요일부터 시작
    return recordRepository
        .findByDateBetweenAndUserWithConsumptions(
            date.minusDays((date.getDayOfWeek().getValue() % 7)),
            date.plusDays(6 - (date.getDayOfWeek().getValue() % 7)),
            userId)
        .stream()
        .map(RecordDto::withConsumptions)
        .collect(Collectors.toList());
  }

  public List<RecordDto> getMonthlyRecord(String month, Long userId) {
    return recordRepository
        .findByDateBetweenAndUser(
            LocalDate.parse(month + "-01"),
            LocalDate.parse(month + "-01").plusMonths(1).minusDays(1),
            userId)
        .stream()
        .map(RecordDto::from)
        .collect(Collectors.toList());
  }

  public List<RecordDto> getMonthlyRecordWithConsumptions(String month, Long userId) {
    return recordRepository
        .findByDateBetweenAndUserWithConsumptions(
            LocalDate.parse(month + "-01"),
            LocalDate.parse(month + "-01").plusMonths(1).minusDays(1),
            userId)
        .stream()
        .map(RecordDto::withConsumptions)
        .collect(Collectors.toList());
  }
}
