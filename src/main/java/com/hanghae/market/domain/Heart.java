package com.hanghae.market.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Heart {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public void addBoard(Board board) {
        this.board = board;
        board.getHearts().add(this);
    }


}
