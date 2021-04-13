package com.hanghae.market.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupReqeustDto {

    @NotBlank(message = "아이디를 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{4,12}$",
            message = "아이디는 숫자와 영어를 포함한 4-12글자여야합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 비워둘 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문 대소문자와 숫자를 포함한 8-20자여야합니다.")
    private String password;

    @NotBlank(message = "이메일을 비워둘 수 없습니다.")
    @Email(message = "메일 양식을 지켜주세요.")
    private String email;

    private String myself;

    //    @Pattern(regexp = "/([^\\s]+(?=\\.(jpg|gif|png))\\.\\2)/",
//            message = "jpg.gif.png파일만 가능합니다.")
    private String profile_img;

    @NotBlank(message = "주소를 비워둘 수 없습니다.")
    private String city;
    @NotBlank(message = "주소를 비워둘 수 없습니다.")
    private String street;


    public SignupReqeustDto(String username, String password, String email, String myself, String profile_img, String city, String street) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.myself = myself;
        this.profile_img = profile_img;
        this.city = city;
        this.street = street;
    }
}
