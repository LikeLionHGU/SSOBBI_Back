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
    User user =
        userRepository
            .findByKakaoId(dto.getKakaoId())
            .orElseGet(() -> userRepository.save(User.from(dto)));
    user.setProfileImageUrl(dto.getProfileImageUrl());
    user.setName(dto.getName());
    return UserDto.from(user);
  }

  public User getLoginUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
  }
}
