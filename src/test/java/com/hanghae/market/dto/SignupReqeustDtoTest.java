package com.hanghae.market.dto;

import com.hanghae.market.model.User;
import org.junit.jupiter.api.*;
import javax.validation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SignupReqeustDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("회원생성")
    class CreateUser{

        private Long id;
        private String username;
        private String password;
        private String email;
        private String city;
        private String street;
        private String myself;
        private String profile_img;


        @BeforeEach
        void setup(){

            id = 100L;
            username = "test33";
            password ="d23sdfsdDf3234523423&*#*";
            email ="test1@naver.com";
            city = "서울";
            street = "강남";
            myself = "자기소개";
            profile_img ="test.png";

        }

        @Test
        @DisplayName("정상케이스")
        void createUser_Normal(){
            //given

            SignupReqeustDto reqeustDto =SignupReqeustDto.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .city(city)
                    .street(street)
                    .myself(myself)
                    .profile_img(profile_img)
                    .build();
            //when
            User user = reqeustDto.toEntity();

            //then
            assertNull(user.getId());
            assertEquals(username,user.getUsername());
            assertEquals(password,user.getPassword());
            assertEquals(email,user.getEmail());
            assertEquals(city, user.getAddress().getCity());
            assertEquals(street,user.getAddress().getStreet());
            assertEquals(myself,user.getMyself());
        }

        @Nested
        @DisplayName("실패 케이스")
        class  FailClass{
            @Nested
            @DisplayName("회원 ID")
            class username{
                @Test
                @DisplayName("null")
                void fail(){
                    //given
                    username = null;

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("아이디를 비워둘 수 없습니다.");
                }
                @Test
                @DisplayName("한글")
                void fail2(){
                    //given
                    username = "한글";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("아이디는 숫자와 영어를 포함한 4-12글자여야합니다.");
                }
                @Test
                @DisplayName("영어만")
                void fail3(){
                    //given
                    username = "test";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("아이디는 숫자와 영어를 포함한 4-12글자여야합니다.");
                }
                @Test
                @DisplayName("숫자만")
                void fai4(){
                    //given
                    username = "111";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("아이디는 숫자와 영어를 포함한 4-12글자여야합니다.");
                }
                @Test
                @DisplayName("영어랑 숫자포함해서 4글자 이하")
                void fail5(){
                    //given
                    username = "t1t";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("아이디는 숫자와 영어를 포함한 4-12글자여야합니다.");
                }

            }
            @Nested
            @DisplayName("비밀번호")
            class password{
                @Test
                @DisplayName("null")
                void fail(){
                    //given
                    password="";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호를 비워둘 수 없습니다.");
                }
                @Test
                @DisplayName("한글")
                void fail2(){
                    //given
                    password = "한글";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("영어만")
                void fail3(){
                    //given
                    username = "test";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("숫자만")
                void fail4(){
                    //given
                    password = "111";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("영어대소문자,숫자만")
                void fail5(){
                    //given
                    password = "1eeeeessD11";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("영어소문자,숫자만")
                void fail6(){
                    //given
                    password = "eeeeee111";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("영어 소문자,특수문자만")
                void fail7(){
                    //given
                    password = "test####";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }
                @Test
                @DisplayName("영어 대소문자,숫자,특수문자포함해서 8글자 이하")
                void fail20(){
                    //given
                    username = "Qq1#";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("비밀번호는 영문 대소문자와 숫자,특수문자를 포함한 8-20자여야합니다.");
                }

            }
            @Nested
            @DisplayName("이메일")
            class email{
                @Test
                @DisplayName("null")
                void fail(){
                    //given
                    email = null;

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("이메일을 비워둘 수 없습니다.");
                }
                @Test
                @DisplayName("한글")
                void fail2(){
                    //given
                    email = "한글";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("메일 양식을 지켜주세요.");
                }
                @Test
                @DisplayName("영어만")
                void fail3(){
                    //given
                    email = "emailnaver.com";

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("메일 양식을 지켜주세요.");
                }

            }
            @Nested
            @DisplayName("시")
            class city{
                @Test
                @DisplayName("null")
                void fail(){
                    //given
                    city = null;

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("주소를 비워둘 수 없습니다.");
                }

            }
            @Nested
            @DisplayName("길")
            class street{
                @Test
                @DisplayName("null")
                void fail(){
                    //given
                    street = null;

                    SignupReqeustDto reqeustDto = new SignupReqeustDto(username,password,email,myself,profile_img,city,street);
                    //when
                    Set<ConstraintViolation<SignupReqeustDto>> violations  = validator.validate(reqeustDto);
                    Iterator<ConstraintViolation<SignupReqeustDto>> iterator = violations.iterator();

                    List<String> messages = new ArrayList<>();
                    while (iterator.hasNext()) {
                        ConstraintViolation<SignupReqeustDto> next = iterator.next();
                        messages.add(next.getMessage());
                    }
                    assertThat(messages).contains("주소를 비워둘 수 없습니다.");
                }

            }
        }

    }


}