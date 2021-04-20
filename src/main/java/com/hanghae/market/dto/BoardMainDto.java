package com.hanghae.market.dto;

import com.hanghae.market.model.Board;
import lombok.Getter;

@Getter
public class BoardMainDto {
    private Long id;
    private String title;
    private String content;
    private int price;
    private String imgUrl;
    private Long userId;

    public BoardMainDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.price = board.getPrice();
        this.imgUrl = board.getImgUrl();
        this.userId = board.getUser().getId();
    }
}
