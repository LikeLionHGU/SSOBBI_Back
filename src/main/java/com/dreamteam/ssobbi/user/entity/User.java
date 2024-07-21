package com.dreamteam.ssobbi.user.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.user.dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String name;

    private String phoneNumber;


    public static User from(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .kakaoId(dto.getKakaoId())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
