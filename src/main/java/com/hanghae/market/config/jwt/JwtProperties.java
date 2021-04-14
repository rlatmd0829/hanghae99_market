package com.hanghae.market.config.jwt;

//jwt 토큰을 발급하는데 사용되는 값을 interface로 구현
public interface JwtProperties {
    String SECRET ="market_secretkey"; // 서버만 알고있는 비밀 값
    int EXPIRATION_TIME =60000*10;//10일 (1/1000초)
    String TOKEN_PREFIX ="Bearer ";
    String HEADER_STRING ="Authorization";
}