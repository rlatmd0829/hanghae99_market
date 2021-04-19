package com.hanghae.market.model;

import com.hanghae.market.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

    public void addUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }

    public void addBoard(Board board) {
        this.board = board;
        board.getComments().add(this);
    }

    public void update(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }
}
