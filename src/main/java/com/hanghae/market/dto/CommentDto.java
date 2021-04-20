package com.hanghae.market.dto;

import com.hanghae.market.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String comment;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }

    public void update(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
    }
}
