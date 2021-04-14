package com.hanghae.market.service;

import com.hanghae.market.dto.BoardRequestDto;
import com.hanghae.market.domain.Board;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.BoardRepository;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Board> getBoard() {
        List<Board> board = boardRepository.findAll();
        System.out.println(board);
        return board;
    }

    public void createBoard(BoardRequestDto requestDto, Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = new Board(requestDto);
        board.addUser(user);
        boardRepository.save(board);

    }

    @Transactional
    public Board updateBoard(Long board_id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        board.update(requestDto);
        return board;
    }

    @Transactional
    public void deleteBoard(Long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        boardRepository.deleteById(board_id);
    }
}
