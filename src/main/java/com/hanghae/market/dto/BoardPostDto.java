package com.hanghae.market.dto;

import com.hanghae.market.model.Board;
import lombok.Getter;

@Getter
public class BoardPostDto {
    private Long boardId;

    public BoardPostDto(Board board) {
        this.boardId = board.getId();
    }
}
