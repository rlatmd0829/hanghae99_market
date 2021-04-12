package com.hanghae.market.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private int price;
    private boolean status;
    private boolean exchange;
    private String imgUrl;
}
