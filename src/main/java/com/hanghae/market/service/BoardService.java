package com.hanghae.market.service;

import com.hanghae.market.dto.BoardMainDto;
import com.hanghae.market.dto.BoardRequestDto;
import com.hanghae.market.model.Board;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.BoardRepository;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<BoardMainDto> getBoard() {
        List<Board> board = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardMainDto> mainDtoList = new ArrayList<>();
        // main에 필요한 값들만 Dto로 만들어서 보내준다.
        for(int i=0; i<board.size(); i++){
            BoardMainDto mainDto = new BoardMainDto(board.get(i));
            mainDtoList.add(mainDto);
        }
        return mainDtoList;
    }

    public List<BoardMainDto> getSearchBoard(String title) {
        List<Board> board = boardRepository.findByTitleContainingOrContentContaining(title, title);
        List<BoardMainDto> mainDtoList = new ArrayList<>();
        // main에 필요한 값들만 Dto로 만들어서 보내준다.
        for(int i=0; i<board.size(); i++){
            BoardMainDto mainDto = new BoardMainDto(board.get(i));
            mainDtoList.add(mainDto);
        }
        return mainDtoList;
    }

    public void createBoard(BoardRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = new Board(requestDto);
        board.addUser(user); // 연관관계 추가 (댓글 만들어지면 또 댓글도 추가해야함)
        boardRepository.save(board);

    }

    @Transactional
    public Board updateBoard(Long boardId, BoardRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (board.getUser().getId().equals(userId)){
            board.update(requestDto);
            return board;
        }
        else{
            return null;
        }

    }

    @Transactional
    public Board deleteBoard(Long boardId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (board.getUser().getId().equals(userId)) {
            boardRepository.deleteById(boardId);
            return board;
        }
        else{
            return null;
        }
    }


    public Board getDetailBoard(Long boardId, Long id) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return board;
    }


}
