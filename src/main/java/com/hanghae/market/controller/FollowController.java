package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.model.Follow;
import com.hanghae.market.model.Message;
import com.hanghae.market.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class FollowController {

    final
    FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/users/{followUserId}/follow")
    public HashMap<String, Object> getFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){
        return followService.getFollow(followUserId, userDetails.getUser().getId());
    }

    @PostMapping("/users/{followUserId}/follow")
    public ResponseEntity createFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){
        if (followUserId.equals(userDetails.getUser().getId())){
            Message message = new Message("자신을 팔로우 할 수 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Follow follow = followService.createFollow(followUserId, userDetails.getUser().getId());
        if(follow == null){
            Message message = new Message("이미 팔로우 상태입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{followUserId}/follow")
    public ResponseEntity deleteFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){
        Follow follow = followService.deleteFollow(followUserId, userDetails.getUser().getId());
        if(follow == null){
            Message message = new Message("취소할 팔로우가 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
