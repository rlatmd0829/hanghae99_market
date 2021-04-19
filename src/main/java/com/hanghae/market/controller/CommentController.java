package com.hanghae.market.controller;

import com.hanghae.market.config.auth.PrincipalDetails;
import com.hanghae.market.dto.CommentDto;
import com.hanghae.market.model.Comment;
import com.hanghae.market.model.Message;
import com.hanghae.market.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{boardId}/comments")
    public List<CommentDto> getComment(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails){
        return commentService.getComment(boardId, userDetails.getUser().getId());
    }

    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity postComment(@PathVariable Long boardId, @AuthenticationPrincipal PrincipalDetails userDetails, @RequestBody CommentDto commentDto){
        System.out.println(commentDto.getComment());
        Comment comment = commentService.postComment(boardId, userDetails.getUser().getId(), commentDto);
        if(comment == null){
            Message message = new Message("댓글 작성 오류입니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/boards/{boardId}/comments/{commentId}")
    public HashMap<String, Object> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails userDetails,
                                 @RequestBody CommentDto commentDto){
        CommentDto comment = commentService.updateComment(boardId, commentId, userDetails.getUser().getId(), commentDto);
        HashMap<String, Object> hashMap = new HashMap<>();
        if(comment == null){
            hashMap.put("message", "자신이 작성한 댓글만 수정 할 수 있습니다.");
            return hashMap;
        }else{
            hashMap.put("comment", comment);
            return hashMap;
        }

    }

    @DeleteMapping("/boards/{boardId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails userDetails){

        Comment comment = commentService.deleteComment(boardId, commentId, userDetails.getUser().getId());
        if(comment == null){
            Message message = new Message("자신이 작성한 댓글만 삭제할 수 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            return ResponseEntity.ok().build();
        }
    }
}
