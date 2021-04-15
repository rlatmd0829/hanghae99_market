package com.hanghae.market.model;

import com.hanghae.market.model.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User followUser;


    public void addUser(User user) {
        this.user = user;
        user.getRequestUsers().add(this);
    }

    public void addFollowUser(User followUser) {
        this.followUser = followUser;
        followUser.getResponseUsers().add(this);
    }
}
