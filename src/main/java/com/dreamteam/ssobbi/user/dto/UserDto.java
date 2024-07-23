package com.dreamteam.ssobbi.user.dto;

import com.dreamteam.ssobbi.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

  private Long id;
  private Long kakaoId;
  private String name;
  private String phoneNumber;
  private String profileImageUrl;

  public static UserDto from(User user) {
    return UserDto.builder()
        .id(user.getId())
        .kakaoId(user.getKakaoId())
        .name(user.getName())
        .build();
  }
}
