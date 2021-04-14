package com.hanghae.market.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long id;
    private String name;

    public UserInfoDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
