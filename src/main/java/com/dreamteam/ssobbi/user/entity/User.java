package com.dreamteam.ssobbi.user.entity;

import com.dreamteam.ssobbi.base.entity.BaseTime;
import com.dreamteam.ssobbi.user.dto.UserDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    private Long kakaoId;

    @Column(columnDefinition = "varchar(200)")
    private String name;

    @Setter
    @Column(columnDefinition = "varchar(20)")
    private String phoneNumber;

    @Column(columnDefinition = "varchar(500)")
    private String profileImageUrl;

    public static User from(UserDto dto){
        return User.builder()
                .id(dto.getId())
                .kakaoId(dto.getKakaoId())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .profileImageUrl(dto.getProfileImageUrl())
                .build();
    }
}
