package com.hanghae.market.service;


import com.hanghae.market.config.kakao.KakaoJwt;
import com.hanghae.market.dto.KakaoLoginInfoDto;
import com.hanghae.market.dto.KakaoUserInfoDto;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.DoubleStream;

@Service
public class KakaoLoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KakaoJwt kakaoJwt;
    private static final String PASSWORD_GARBAGE = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public KakaoLoginService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, KakaoJwt kakaoJwt) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kakaoJwt = kakaoJwt;
    }

    public KakaoLoginInfoDto kakaoSave(String authorizedCode) {

        //카카오 userInfo를 받아옴
        KakaoUserInfoDto kakaoUserInfo = kakaoJwt.getUserInfo(authorizedCode);
        Long id = kakaoUserInfo.getId();
        String nickname = kakaoUserInfo.getNickname();
        String email = kakaoUserInfo.getEmail();
        String kakaoId = id.toString();

        //받아온 userInfo로 회원중에 일치하는 회원이 존재하는지 확인.
        User kakaoUser = userRepository.findByKakaoId(kakaoId);

        KakaoLoginInfoDto kakaoLoginInfoDto = null;

        if (kakaoUser != null) {

                kakaoLoginInfoDto = new KakaoLoginInfoDto(kakaoUser.getUsername(), kakaoId + PASSWORD_GARBAGE);
                return kakaoLoginInfoDto;

        } else if (kakaoUser == null) {

            String username = kakaoId;
            String password = kakaoId + PASSWORD_GARBAGE;
            String encodePasswrod = bCryptPasswordEncoder.encode(password);

            kakaoUser = new User(username, encodePasswrod, email, kakaoId);
            userRepository.save(kakaoUser);

            kakaoLoginInfoDto = new KakaoLoginInfoDto(username, password);
            return kakaoLoginInfoDto;
        }

        return kakaoLoginInfoDto;
    }
}
