package com.hanghae.market.service;

import com.hanghae.market.dto.CommentDto;
import com.hanghae.market.model.Board;
import com.hanghae.market.model.Comment;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.BoardRepository;
import com.hanghae.market.repository.CommentRepository;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<CommentDto> getComment(Long boardId, Long userId) {
        List<Comment> comment = commentRepository.findByBoardId(boardId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(int i=0; i<comment.size(); i++){
            CommentDto commentDto = new CommentDto(comment.get(i));
            commentDtoList.add(commentDto);
        }
        return commentDtoList;

    }


    public Comment postComment(Long boardId, Long userId, CommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentDto);
        comment.addUser(user);
        comment.addBoard(board);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public CommentDto updateComment(Long boardId, Long commentId, Long userId, CommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (userId.equals(board.getUser().getId())) {
            comment.update(commentDto);
            commentDto.update(comment);
            return commentDto;

        }else{
            return null;
        }

    }

    public Comment deleteComment(Long boardId, Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (userId.equals(board.getUser().getId())){
            commentRepository.deleteById(commentId);
            return comment;
        }else{
            return null;
        }

    }
}
