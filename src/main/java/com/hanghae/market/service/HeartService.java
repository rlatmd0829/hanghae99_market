package com.hanghae.market.service;

import com.hanghae.market.domain.Board;
import com.hanghae.market.domain.Heart;
import com.hanghae.market.repository.BoardRepository;
import com.hanghae.market.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class HeartService {

    final
    HeartRepository heartRepository;

    final
    BoardRepository boardRepository;

    public HeartService(HeartRepository heartRepository, BoardRepository boardRepository) {
        this.heartRepository = heartRepository;
        this.boardRepository = boardRepository;
    }

    public HashMap<String,Object> getHeart(Long board_id) {
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardId(board_id); // user_id 추가해야함
        //List<Heart> heartCount = heartRepository.findByBoardId(board_id);
        //Integer Count = heartCount.size();
        HashMap<String,Object> hashMap = new HashMap<>();
        if(heart == null){
            hashMap.put("check", false);
            //hashMap.put("heartCount", Count);
            return hashMap;
        }else{
            hashMap.put("check", true);
            //hashMap.put("heartCount", Count);
            return hashMap;
        }
    }

    public Heart createHeart(Long board_id){
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardId(board_id); // user_id 추가해야함
        if(heart == null){
            Heart newHeart = new Heart();
            newHeart.addBoard(board);
            heartRepository.save(newHeart);
            return newHeart;
        }else{
            return null;
        }
    }

    @Transactional
    public Heart DeleteHeart(Long board_id){
        Board board = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Heart heart = heartRepository.findByBoardId(board_id); // user_id 추가해야함
        if(heart == null){
            return null;
        }else{
            heartRepository.deleteById(heart.getId());
            return heart;
        }

    }
}
