package com.hanghae.market.dto;

import com.hanghae.market.model.Board;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private int price;
    //private boolean status;
    //private boolean exchange;
    private String imgUrl;

    public BoardRequestDto(String title, String content, int price, boolean status, boolean exchange, String imgUrl) {
        this.title = title;
        this.content = content;
        this.price = price;
        //this.status = status;
        //this.exchange = exchange;
        this.imgUrl = imgUrl;
    }

    public BoardRequestDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.price = board.getPrice();
        //this.status = board.isStatus();
        //this.exchange = board.isExchange();
        this.imgUrl = board.getImgUrl();
    }
}
