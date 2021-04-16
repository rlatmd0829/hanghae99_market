package com.hanghae.market.controller;


import com.hanghae.market.dto.KakaoLoginInfoDto;
import com.hanghae.market.service.KakaoLoginService;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class KakaoLoginController {

    /*
    * 카카오 회원 정보를 받아서 HttpClinet를 통해 항해마켓 서버에게 로그인을 요청하는 파일
    */
    private final KakaoLoginService kakaoLoginService;

    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }


    @GetMapping("/kakao/callback")
    public void kakaoLogin(String code,HttpServletResponse response) throws IOException {

//        System.out.println("카카오톡 ");

        //카카오에서 user정보를 받아와서 DB 저장 . -> DB에 저장된 username과 password받아옴.
        KakaoLoginInfoDto kakaoLoginInfo =kakaoLoginService.kakaoSave(code);

//        System.out.println(kakaoLoginInfo.getKakaoId());
//        System.out.println(kakaoLoginInfo.getPassword());

        //저장한 kakaoUser정보로 로그인요청
        if(kakaoLoginInfo != null){

            String username = kakaoLoginInfo.getKakaoId();
            String password = kakaoLoginInfo.getPassword();

            //HttpPost 요청
            HttpClient client = HttpClientBuilder.create().build();
            String postUrl ="http://localhost:8080/login";
            HttpPost httpPost = new HttpPost(postUrl);
            String data = "{" +
                    "\"username\": \""+username+"\", " +
                    "\"password\": \""+password+"\""+
                    "}";

            StringEntity entity = new StringEntity(data, ContentType.APPLICATION_FORM_URLENCODED);
            httpPost.setEntity(entity);

            HttpResponse responsePost = client.execute(httpPost);

            //HttpPost요청이 정상적으로 완료 되었다면
            if (responsePost.getStatusLine().getStatusCode() == 200) {

                // response Body에 있는 값을 꺼냄
                HttpEntity entitys = responsePost.getEntity();
                String content = EntityUtils.toString(entitys);

                // response header에 있는 token꺼냄
                String value = responsePost.getFirstHeader("Authorization").getValue();

                //다시 진짜 사용자의 요청에 리턴해 줄 response에 토큰과 사용자 정보를 넣는다.
                response.addHeader("Authonrazation", value);
                response.getWriter().write(content);

            } else {
                //에러 처리.
                response.getWriter().write("kakaoLoginError");
            }

        }else{
            //에러처리
            response.getWriter().write("kakaoUserNotFount");
        }

    }


}
