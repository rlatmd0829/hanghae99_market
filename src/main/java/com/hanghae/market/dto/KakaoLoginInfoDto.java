package com.hanghae.market.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoLoginInfoDto {

    //카카오 user 정보로 login하기위한 Dto
    private String kakaoId;
    private String password;

    @Builder
    public KakaoLoginInfoDto(String kakaoId,String password){
        this.kakaoId = kakaoId;
        this.password = password;
    }
}
