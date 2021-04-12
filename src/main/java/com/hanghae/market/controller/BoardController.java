package com.hanghae.market.controller;

import com.hanghae.market.dto.BoardRequestDto;
import com.hanghae.market.domain.Board;
import com.hanghae.market.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping("/main")
    public List<Board> getBoard(){
        return boardService.getBoard();
    }

    @PostMapping("/boards")
    public ResponseEntity createBoard(@RequestBody BoardRequestDto requestDto){
        boardService.createBoard(requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/boards/{board_id}")
    public Board updateBoard(@PathVariable Long board_id, BoardRequestDto requestDto){
        return boardService.updateBoard(board_id, requestDto);
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable Long board_id){
        boardService.deleteBoard(board_id);
        return ResponseEntity.ok().build();
    }

}
