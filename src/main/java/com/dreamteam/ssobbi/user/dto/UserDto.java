package com.dreamteam.ssobbi.user.dto;

import com.dreamteam.ssobbi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
public class UserDto {

  private Long id;
  private Long kakaoId;
  private String name;
  @Setter private String phoneNumber;
  @Setter private String profileImageUrl;

  public static UserDto from(User user) {
    return UserDto.builder()
        .id(user.getId())
        .kakaoId(user.getKakaoId())
        .name(user.getName())
        .phoneNumber(user.getPhoneNumber())
        .profileImageUrl(user.getProfileImageUrl())
        .build();
  }

  public static ArrayList<UserDto> from(ArrayList<User> users) {
    ArrayList<UserDto> userDtos = new ArrayList<>();
    for (User user : users) {
      userDtos.add(UserDto.builder()
          .id(user.getId())
          .kakaoId(user.getKakaoId())
          .name(user.getName())
          .phoneNumber(user.getPhoneNumber())
          .profileImageUrl(user.getProfileImageUrl())
          .build());
    }
    return userDtos;
  }
}
