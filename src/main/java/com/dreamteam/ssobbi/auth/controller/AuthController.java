package com.dreamteam.ssobbi.auth.controller;

import com.dreamteam.ssobbi.auth.controller.reponse.KakaoLoginResponse;
import com.dreamteam.ssobbi.auth.service.AuthService;
import com.dreamteam.ssobbi.auth.service.KakaoService;
import com.dreamteam.ssobbi.auth.util.JwtUtil;
import com.dreamteam.ssobbi.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  private final KakaoService kakaoService;

  @Value("${custom.jwt.secret}")
  private String SECRET_KEY;

  @Value("${custom.jwt.expire-time-ms}")
  private long EXPIRE_TIME_MS;

  @GetMapping("/api/ssobbi/auth/kakao-login")
  public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestParam String code) {
    UserDto userDto =
        authService.kakaoLogin(
            kakaoService.kakaoLogin(code, "http://localhost:3000/login/oauth/kakao"));
    return ResponseEntity.ok(
        KakaoLoginResponse.builder()
            .accessToken(JwtUtil.createToken(userDto.getId(), SECRET_KEY, EXPIRE_TIME_MS))
            .name(userDto.getName())
            .profileImageUrl(userDto.getProfileImageUrl())
            .phoneNumber(userDto.getPhoneNumber())
            .build());
  }
}
