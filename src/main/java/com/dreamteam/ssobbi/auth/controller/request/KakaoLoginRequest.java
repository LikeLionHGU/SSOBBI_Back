package com.dreamteam.ssobbi.auth.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoLoginRequest {
    private String kakaoToken;
}
