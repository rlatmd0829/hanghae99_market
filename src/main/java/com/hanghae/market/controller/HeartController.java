package com.hanghae.market.controller;

import com.hanghae.market.domain.Heart;
import com.hanghae.market.domain.Message;
import com.hanghae.market.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class HeartController {

    final
    HeartService heartService;

    public HeartController(HeartService heartService) {
        this.heartService = heartService;
    }

    @GetMapping("/boards/{board_id}/heart")
    public HashMap<String, Object> getHeart(@PathVariable Long board_id) {
        return heartService.getHeart(board_id);
    }

    @PostMapping("/boards/{board_id}/heart")
    public ResponseEntity createHeart(@PathVariable Long board_id){
        Heart heart = heartService.createHeart(board_id);
        if(heart == null){
            Message message = new Message("이미좋아요 상태입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/boards/{board_id}/heart")
    public ResponseEntity deleteHeart(@PathVariable Long board_id){
        Heart heart = heartService.DeleteHeart(board_id);
        if(heart == null){
            Message message = new Message("취소할 좋아요가 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }
}
