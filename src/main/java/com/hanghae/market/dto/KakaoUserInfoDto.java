package com.hanghae.market.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KakaoUserInfoDto {
    //kakao에서 받아오는 정보
    Long id;
    String email;
    String nickname;

    public KakaoUserInfoDto(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}