//package com.hanghae.market.controller;
//
//import com.hanghae.market.config.auth.PrincipalDetails;
//import com.hanghae.market.dto.BoardMainDto;
//import com.hanghae.market.dto.BoardRequestDto;
//import com.hanghae.market.model.Board;
//import com.hanghae.market.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
////import com.hanghae.market.s3.S3Uploader;
//
//
//@RestController
//@RequiredArgsConstructor
//public class BoardController {
//
//    private final BoardService boardService;
////    private final S3Uploader s3Uploader;
//
//
//    @GetMapping("/main")
//    public List<BoardMainDto> getBoard(){
//        return boardService.getBoard();
//    }
//
//
//    @PostMapping("/boards")
//    public ResponseEntity createBoard(@RequestParam("title") String title, @RequestParam("content") String content,
//                                      @RequestParam("price") int price, @RequestParam("status") boolean status, @RequestParam("exchange") boolean exchange,
//                                      @RequestParam("file") MultipartFile files, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {
//        String imgUrl = s3Uploader.upload(files, "static");
//        BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);
//        System.out.println(userDetails.getUser().getId());
//        boardService.createBoard(requestDto, userDetails.getUser().getId());
//        return ResponseEntity.ok().build();
//    }
//
////    @PostMapping("/boards")
////    public ResponseEntity createBoard(@RequestBody @RequestParam("requestDto") BoardRequestDto requestDto,
////                                      @RequestParam("file") MultipartFile files, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {
////        String imgUrl = s3Uploader.upload(files, "static");
////        //BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);
////
////        boardService.createBoard(requestDto, userDetails.getUser().getId());
////        return ResponseEntity.ok().build();
////    }
//
//    @PutMapping("/boards/{boardId}")
//    public ResponseEntity updateBoard(@PathVariable Long boardId,@RequestBody BoardRequestDto requestDto){
//        boardService.updateBoard(boardId, requestDto);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/boards/{boardId}")
//    public ResponseEntity deleteBoard(@PathVariable Long boardId){
//        boardService.deleteBoard(boardId);
//        return ResponseEntity.ok().build();
//
//    }
//
//    @GetMapping("/boards/{boardId}/details")
//    public Board getDetailBoard(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
//        return boardService.getDetailBoard(boardId, userDetails.getUser().getId());
//    }
//
//}
