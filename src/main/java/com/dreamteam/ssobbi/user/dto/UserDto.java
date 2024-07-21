package com.dreamteam.ssobbi.user.dto;

import com.dreamteam.ssobbi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class UserDto {

  private Long id;
  private Long kakaoId;
  private String name;
  @Setter private String phoneNumber;

  public static UserDto from(User user) {
    return UserDto.builder()
        .id(user.getId())
        .kakaoId(user.getKakaoId())
        .name(user.getName())
        .phoneNumber(user.getPhoneNumber())
        .build();
  }
}
