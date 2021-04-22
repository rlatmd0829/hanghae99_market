package com.hanghae.market.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Type type;
    private String link;

    //Message인 경우 채팅방번호, 댓글인 경우 게시글 번호, 팔로우인경우 마이페이지 번호
    @ManyToOne
    private User user;

    private LocalDateTime time;

    public Notice(Type type, User user, String content, String link, LocalDateTime time){
        this.type = type;
        this.user = user;
        this.content = content;
        this.link = link;
        this.time = time;
    }
}

