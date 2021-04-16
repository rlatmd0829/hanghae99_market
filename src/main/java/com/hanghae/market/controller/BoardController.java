package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.dto.BoardMainDto;
import com.hanghae.market.dto.BoardRequestDto;
import com.hanghae.market.model.Board;

import com.hanghae.market.model.Message;
import com.hanghae.market.s3.S3Uploader;
import com.hanghae.market.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.hanghae.market.s3.S3Uploader;


@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final S3Uploader s3Uploader;

    //전체게시글 조회, 검색 (메인페이지)
    @GetMapping("/main")
    public List<BoardMainDto> getBoard(@RequestParam(value = "searchText", required = false) String searchText){
        if (searchText == null){
            return boardService.getBoard();
        }else{
            return boardService.getSearchBoard(searchText);
        }
    }

    // 전체게시글 조회, 검색 (메인페이지), 무한스크롤 page는 1부터 받아야한다.
//    @GetMapping("/main")
//    public Page<BoardMainDto> getBoard(@RequestParam("page") int page, @RequestParam(value = "searchText", required = false) String searchText){
//        if (searchText == null){
//            return boardService.getBoard(page);
//        }else{
//            return boardService.getSearchBoard(searchText, page);
//        }
//    }

    // 게시글 작성
    @PostMapping("/boards")
    public ResponseEntity createBoard(@RequestParam("title") String title, @RequestParam("content") String content,
                                      @RequestParam("price") int price, @RequestParam("status") boolean status, @RequestParam("exchange") boolean exchange,
                                      @RequestParam("file") MultipartFile files, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {

        String imgUrl = s3Uploader.upload(files, "static");
        BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);

        boardService.createBoard(requestDto, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/boards")
//    public ResponseEntity createBoard(@RequestBody @RequestParam("requestDto") BoardRequestDto requestDto,
//                                      @RequestParam("file") MultipartFile files, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {
//        String imgUrl = s3Uploader.upload(files, "static");
//        //BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);
//
//        boardService.createBoard(requestDto, userDetails.getUser().getId());
//        return ResponseEntity.ok().build();
//    }

    // 게시글 수정
    @PutMapping("/boards/{boardId}")
    public ResponseEntity updateBoard(@PathVariable Long boardId, @RequestParam("title") String title, @RequestParam("content") String content,
                                      @RequestParam("price") int price, @RequestParam("status") boolean status, @RequestParam("exchange") boolean exchange,
                                      @RequestParam("file") MultipartFile files, @RequestParam(value = "imgUrl", required = false) String imgUrl, @AuthenticationPrincipal PrincipalDetails userDetails) throws IOException {

        // 이미지 수정없이 게시글 수정할 때는 s3에 업로드 할 필요 없으므로 imgUrl이 안넘어 올 경우에만 업로드를 시켜준다.
        if(imgUrl == null) {
            imgUrl = s3Uploader.upload(files, "static");
        }
        // 이미지를 수정안한 상태에서 보낼경우 또 업로드 하지않게 만들어야 할듯

        BoardRequestDto requestDto = new BoardRequestDto(title, content, price, status, exchange, imgUrl);

        Board board = boardService.updateBoard(boardId, requestDto, userDetails.getUser().getId());
        if (board==null){
            Message message = new Message("자신이 작성한 게시글만 수정할 수 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
        Board board = boardService.deleteBoard(boardId, userDetails.getUser().getId());
        if (board==null){
            Message message = new Message("자신이 작성한 게시글만 삭제할 수 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().build();

    }

    // 게시글 상세페이지
    @GetMapping("/boards/{boardId}/details")
    public Board getDetailBoard(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
        return boardService.getDetailBoard(boardId, userDetails.getUser().getId());
    }

}
