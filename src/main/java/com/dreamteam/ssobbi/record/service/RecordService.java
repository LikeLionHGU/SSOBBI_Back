package com.dreamteam.ssobbi.record.service;

import com.dreamteam.ssobbi.base.exception.AlreadyExistException;
import com.dreamteam.ssobbi.base.exception.NotFoundException;
import com.dreamteam.ssobbi.record.dto.RecordDto;
import com.dreamteam.ssobbi.record.entity.Record;
import com.dreamteam.ssobbi.record.repository.RecordRepository;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {
  private final RecordRepository recordRepository;
  private final UserRepository userRepository;

  public void addRecord(RecordDto dto, Long userId) {
    if(recordRepository.existsByUser_IdAndDate(userId, dto.getDate())) {
      throw new AlreadyExistException("이미 해당 날짜의 기록이 존재합니다.");
    }
    recordRepository.save(
        Record.from(
            userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("유저가 존재하지 않습니다.")),
            dto));
  }
}
