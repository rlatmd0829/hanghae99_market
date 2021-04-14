package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.dto.BoardRequestDto;
import com.hanghae.market.domain.Board;
import com.hanghae.market.s3.S3Uploader;
import com.hanghae.market.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final S3Uploader s3Uploader;


    @GetMapping("/main")
    public List<Board> getBoard(){
        return boardService.getBoard();
    }


    @PostMapping("/boards")
    public ResponseEntity createBoard(@RequestParam("title") String title, @RequestParam("content") String content,
                                      @RequestParam("price") int price, @RequestParam("status") boolean status, @RequestParam("exchange") boolean exchange,
                                      @RequestParam("file") MultipartFile files, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {
        String imgUrl = s3Uploader.upload(files, "static");
        BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);
        System.out.println(userDetails.getUser().getId());
        boardService.createBoard(requestDto, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/boards/{board_id}")
    public ResponseEntity updateBoard(@PathVariable Long board_id,@RequestBody BoardRequestDto requestDto){
        boardService.updateBoard(board_id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/boards/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable Long board_id){
        boardService.deleteBoard(board_id);
        return ResponseEntity.ok().build();

    }

}
