package com.hanghae.market.config.kakao;


import com.hanghae.market.dto.KakaoUserInfoDto;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoJwt {
    /*
    * 카카오에게 요청해서 카카오 유저 정보를 받아오는 코드입니다.
    *
    * */


    public KakaoUserInfoDto getUserInfo(String authorizedCode) {
        // 1. 인가코드 -> 액세스 토큰
        String accessToken = getAccessToken(authorizedCode);
        // 2. 액세스 토큰 -> 카카오 사용자 정보
        KakaoUserInfoDto userInfo = getUserInfoByToken(accessToken);

        return userInfo;
    }

    private String getAccessToken(String authorizedCode){
        //HttpHeader 오브젝트 생성

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "3fb7a0d0879fdec03676106d86b93217");
        params.add("redirect_uri", "http://localhost:8080/kakao/callback");
        params.add("code", authorizedCode);

        //HttpHeader와 httpBody를 하나의 오브젝트에 담기.
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest= new HttpEntity<>(params,headers);

        //Http 요청
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //JSON -> 엑세스 토큰 파싱
        String tokenJson = response.getBody();
        JSONObject rjson = new JSONObject(tokenJson);
        String accessToken = rjson.getString("access_token");

        return accessToken;
    }


    private KakaoUserInfoDto getUserInfoByToken(String accessToken){

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        // Http 요청
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JSONObject body = new JSONObject(response.getBody()); //사용자의 정보
        Long id = body.getLong("id"); //Kakao id값
        String email = body.getJSONObject("kakao_account").getString("email"); // 이메일
        String nickname = body.getJSONObject("properties").getString("nickname"); //닉네임

        return new KakaoUserInfoDto(id, email, nickname); //가져온 정보 담아서 객체 생성

    }


}
