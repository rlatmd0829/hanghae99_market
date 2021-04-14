package com.hanghae.market.model;

import com.hanghae.market.model.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
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
