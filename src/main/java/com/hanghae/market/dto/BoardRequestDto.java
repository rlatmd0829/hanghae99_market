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

    public BoardRequestDto(String title, String content, int price, boolean status, boolean exchange, String imgUrl) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.status = status;
        this.exchange = exchange;
        this.imgUrl = imgUrl;
    }
}
