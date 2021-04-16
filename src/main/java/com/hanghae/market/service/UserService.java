package com.hanghae.market.service;


import com.hanghae.market.dto.SignupReqeustDto;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder ) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // 회원가입 유효성 체크 시 뜨는 에러를 map에 넣어 반환
    public Map<String,String> validateHandling(Errors errors){
        Map<String,String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = error.getField();
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }
        return validatorResult;
    }

    public String signup(SignupReqeustDto reqeustDto){

        /* 비밀번호 암호화 */
        String encodPassword = bCryptPasswordEncoder.encode(reqeustDto.getPassword());
        reqeustDto.setPassword(encodPassword);

        User user = new User(reqeustDto);
        userRepository.save(user);
        return "ture";
    }

    public boolean usernameCheck(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean emailCheck(String email){
        return userRepository.existsByEmail(email);
    }


}
