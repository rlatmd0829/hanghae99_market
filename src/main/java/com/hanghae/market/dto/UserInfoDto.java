package com.hanghae.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long id;
    private String name;
    private String email;

    public UserInfoDto(Long id, String name,String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
