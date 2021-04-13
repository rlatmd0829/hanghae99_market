package com.hanghae.market.controller;


import com.hanghae.market.dto.SignupReqeustDto;
import com.hanghae.market.service.UserService;
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

    /* 회원가입 */
    @PostMapping("/signups")
    public Map<String,String> signup(@RequestBody @Valid SignupReqeustDto reqeustDto, Errors errors)throws Exception{

        /* 에러 메시지 반환 */
        if(errors.hasErrors()){
            return  userService.validateHandling(errors);
        }

        userService.signup(reqeustDto);
        Map<String, String> result = new HashMap<>();
        result.put("ok","true");
        return result;
    }

    /* 아이디(username) 중복 체크 */
    @GetMapping("/signups/username")
    public String username(@RequestBody String username){
        return userService.usernameCheck(username);
    }

    /* 이메일 중복 체크 */
    @GetMapping("/signups/email")
    public String email(@RequestBody String email){
        return userService.emailCheck(email);
    }

}
