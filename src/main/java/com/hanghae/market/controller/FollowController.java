package com.hanghae.market.controller;

import com.hanghae.market.domain.Follow;
import com.hanghae.market.domain.Message;
import com.hanghae.market.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class FollowController {

    final
    FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/users/{follow_user_id}/follow")
    public HashMap<String, Object> getFollow(@PathVariable Long follow_user_id){
        return followService.getFollow(follow_user_id);
    }

    @PostMapping("/users/{follow_user_id}/follow")
    public ResponseEntity createFollow(@PathVariable Long follow_user_id){
        Follow follow = followService.createFollow(follow_user_id);
        if(follow == null){
            Message message = new Message("이미 팔로우 상태입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{follow_user_id}/follow")
    public ResponseEntity deleteFollow(@PathVariable Long follow_user_id){
        Follow follow = followService.deleteFollow(follow_user_id);
        if(follow == null){
            Message message = new Message("취소할 팔로우가 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
