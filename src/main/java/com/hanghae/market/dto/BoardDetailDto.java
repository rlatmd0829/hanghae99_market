package com.hanghae.market.dto;

import com.hanghae.market.model.Board;
import lombok.Getter;

@Getter
public class BoardDetailDto {
    private String title;
    private String content;
    private int price;
    private boolean status;
    private boolean exchange;
    private String imgUrl;
    private Long userId;

    public BoardDetailDto(Board board, Long userId) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.price = board.getPrice();
        this.status = board.isStatus();
        this.exchange = board.isExchange();
        this.imgUrl = board.getImgUrl();
        this.userId = userId;
    }
}