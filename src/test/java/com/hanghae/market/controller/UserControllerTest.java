package com.hanghae.market.controller;

import com.hanghae.market.dto.SignupReqeustDto;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.UserRepository;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("userController테스트")
    class userControllerTest {
        private String username;
        private String password;
        private String email;
        private String city;
        private String street;
        private String myself;

        @BeforeEach
        void setup() {
            username = "test12";
            password = "!123456789h";
            email = "test99@naver.com";
            city = "서울";
            street = "서울역";
            myself = "자기소개";
        }

        @Test
        public void 회원가입() throws Exception {

            //given
            SignupReqeustDto reqeustDto = SignupReqeustDto.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .city(city)
                    .street(street)
                    .myself(myself)
                    .build();
            String url = "http://localhost:" + port + "/signups";

            //when
            ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, reqeustDto, Long.class);

            //then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            List<User> all = userRepository.findAll();

            assertThat(all.get(0).getUsername()).isEqualTo(username);
            assertThat(bCryptPasswordEncoder.matches(all.get(0).getPassword(), password));
            assertThat(all.get(0).getEmail()).isEqualTo(email);
            assertThat(all.get(0).getMyself()).isEqualTo(myself);
            assertThat(all.get(0).getAddress().getCity()).isEqualTo(city);
            assertThat(all.get(0).getAddress().getStreet()).isEqualTo(street);
        }

        @Test
        public void 이메일_중복체크() throws Exception {

            //given
            String url = "http://localhost:" + port + "/signups/email/{email}";

            //when
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class,email);

            //then
            assertThat(responseEntity.getStatusCode().is2xxSuccessful());
            assertThat(responseEntity.getBody()).isEqualTo("true");

        }

        @Test
        public void 회원아이디_중복체크() throws Exception {

            //given
            String url = "http://localhost:" + port + "/signups/username/{username}";

            //when
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class,username);

            //then
            assertThat(responseEntity.getStatusCode().is2xxSuccessful());
            assertThat(responseEntity.getBody()).isEqualTo("true");


        }

    }



}