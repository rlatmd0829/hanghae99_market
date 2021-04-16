package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.model.Follow;
import com.hanghae.market.model.Message;
import com.hanghae.market.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 팔로우 조회(팔로우 상태, 상대 팔로우 횟수)
    @GetMapping("/users/{followUserId}/follow")
    public HashMap<String, Object> getFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){
        return followService.getFollow(followUserId, userDetails.getUser().getId());
    }

    // 팔로우 하기
    @PostMapping("/users/{followUserId}/follow")
    public ResponseEntity createFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){

        // 로그인한 id와 팔로우 할려는 id 가 같을경우 에러
        if (followUserId.equals(userDetails.getUser().getId())){
            Message message = new Message("자신을 팔로우 할 수 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Follow follow = followService.createFollow(followUserId, userDetails.getUser().getId());

        // 이미 팔로우 상태일 경우 delete api를 호출해야 한다.
        if(follow == null){
            Message message = new Message("이미 팔로우 상태입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    // 팔로우 취소
    @DeleteMapping("/users/{followUserId}/follow")
    public ResponseEntity deleteFollow(@PathVariable Long followUserId, @AuthenticationPrincipal PrincipalDetails userDetails){
        Follow follow = followService.deleteFollow(followUserId, userDetails.getUser().getId());

        // 팔로우 상태가 아닐경우 Post api를 호출해야 한다.
        if(follow == null){
            Message message = new Message("취소할 팔로우가 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
