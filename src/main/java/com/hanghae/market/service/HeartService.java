package com.hanghae.market.service;

import com.hanghae.market.model.Board;
import com.hanghae.market.model.Heart;
import com.hanghae.market.model.User;
import com.hanghae.market.repository.BoardRepository;
import com.hanghae.market.repository.HeartRepository;
import com.hanghae.market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 좋아요 조회
    public HashMap<String,Object> getHeart(Long boardId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardIdAndUserId(boardId, userId);

        // 게시글 좋아요 갯수
        List<Heart> heartCount = heartRepository.findByBoardId(boardId);
        Integer Count = heartCount.size();
        HashMap<String,Object> hashMap = new HashMap<>();

        if(heart == null){
            hashMap.put("check", false);
            hashMap.put("heartCount", Count);
            return hashMap;
        }else{
            hashMap.put("check", true);
            hashMap.put("heartCount", Count);
            return hashMap;
        }
    }

    // 좋아요 하기
    public Heart createHeart(Long boardId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardIdAndUserId(boardId, userId); // user_id 추가해야함
        if(heart == null){
            Heart newHeart = new Heart();
            newHeart.addUser(user);
            newHeart.addBoard(board);
            heartRepository.save(newHeart);
            return newHeart;
        }else{
            return null;
        }
    }

    // 좋아요 취소
    @Transactional
    public Heart DeleteHeart(Long boardId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("계정이 존재하지 않습니다.")
        );
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardIdAndUserId(boardId, userId); // user_id 추가해야함
        if(heart == null){
            return null;
        }else{
            // delete시 양방향 연관관계 제거 안해주면 casecade인가 설정 해줘야한다?
            //heart.deleteUser(user);
            //heart.deleteBoard(board);
            heartRepository.deleteById(heart.getId());
            return heart;
        }

    }
}
