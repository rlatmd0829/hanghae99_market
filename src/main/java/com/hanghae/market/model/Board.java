package com.hanghae.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.market.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped{

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private boolean status;
    @Column(nullable = false)
    private boolean exchange;
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.price = requestDto.getPrice();
        this.status = requestDto.isStatus();
        this.exchange = requestDto.isExchange();
        this.imgUrl = requestDto.getImgUrl();
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.price = requestDto.getPrice();
        this.status = requestDto.isStatus();
        this.exchange = requestDto.isExchange();
        this.imgUrl = requestDto.getImgUrl();
    }

    public void addUser(User user) {
        this.user = user;
        user.getBoards().add(this);
    }
}
