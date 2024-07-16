package com.dreamteam.ssobbi.auth.service;

import com.dreamteam.ssobbi.user.dto.UserDto;
import com.dreamteam.ssobbi.user.entity.User;
import com.dreamteam.ssobbi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;

  public UserDto kakaoLogin(UserDto dto) {
    return userRepository
        .findByKakaoId(dto.getKakaoId())
        .map(
            user ->
                UserDto.builder()
                    .id(user.getId())
                    .kakaoId(user.getKakaoId())
                    .name(user.getName())
                    .build())
        .orElseGet(
            () -> {
              User user = User.from(dto);
              userRepository.save(user);
              return UserDto.from(user);
            });
  }
}
