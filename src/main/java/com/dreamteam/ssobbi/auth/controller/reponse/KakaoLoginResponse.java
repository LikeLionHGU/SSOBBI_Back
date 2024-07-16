package com.dreamteam.ssobbi.auth.controller.reponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoLoginResponse {
    private String accessToken;
}
