package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.model.Heart;
import com.hanghae.market.model.Message;
import com.hanghae.market.service.HeartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class HeartController {

    final
    HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @GetMapping("/boards/{boardId}/heart")
    public HashMap<String, Object> getHeart(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails) {
        return heartService.getHeart(boardId, userDetails.getUser().getId());
    }

    @PostMapping("/boards/{boardId}/heart")
    public ResponseEntity createHeart(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
        Heart heart = heartService.createHeart(boardId, userDetails.getUser().getId());
        if(heart == null){
            Message message = new Message("이미좋아요 상태입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/boards/{boardId}/heart")
    public ResponseEntity deleteHeart(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
        Heart heart = heartService.DeleteHeart(boardId, userDetails.getUser().getId());
        if(heart == null){
            Message message = new Message("취소할 좋아요가 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
