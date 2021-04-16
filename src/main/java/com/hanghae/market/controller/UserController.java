package com.hanghae.market.controller;


import com.hanghae.market.dto.SignupReqeustDto;
import com.hanghae.market.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//   연결 테스트
//    @GetMapping("/hello")
//    public String hello(){
//        return "hello";
//    }

    @PostMapping("/signups")
    public ResponseEntity signup(@RequestBody @Valid SignupReqeustDto reqeustDto, Errors errors)throws Exception{
        System.out.println("username :"+reqeustDto.getUsername());
        System.out.println("password :"+reqeustDto.getPassword());
        System.out.println("email :"+reqeustDto.getEmail());
        /* 에러 메시지 반환 */
        if(errors.hasErrors()){
            Map<String, String> error = userService.validateHandling(errors);
            return new ResponseEntity(error, HttpStatus.INSUFFICIENT_STORAGE);
        }

        Boolean usernameCheck = userService.usernameCheck(reqeustDto.getUsername());
        Boolean emailCheck = userService.emailCheck(reqeustDto.getEmail());

        if(usernameCheck){

            //validateHandling의 error와 데이터 형식을 맞춰서 return 해주기 위해서 json형태로..
            Map<String, String> usernameResult = new HashMap<>();
            usernameResult.put("username", "아이디 중복입니다.");
            return new ResponseEntity(usernameResult,HttpStatus.INSUFFICIENT_STORAGE);

        }else if(emailCheck){
            Map<String, String> emailResult = new HashMap<>();
            emailResult.put("email", "이메일 중복입니다");
            return new ResponseEntity(emailResult,HttpStatus.INSUFFICIENT_STORAGE);

            //중복 체크 전부 통과 시에 회원가입
        }else{
            userService.signup(reqeustDto);
        }

        return ResponseEntity.ok().build();
    }

    /* 아이디(username) 중복 체크 */
    @GetMapping("/signups/username")
    public ResponseEntity<Boolean> username(@RequestBody SignupReqeustDto reqeustDto){
        return ResponseEntity.ok(userService.usernameCheck(reqeustDto.getUsername()));
    }

    /* 이메일 중복 체크 */
    @GetMapping("/signups/email")
    public ResponseEntity<Boolean> email(@RequestBody SignupReqeustDto reqeustDto){
        return ResponseEntity.ok(userService.emailCheck(reqeustDto.getEmail()));
    }


}
