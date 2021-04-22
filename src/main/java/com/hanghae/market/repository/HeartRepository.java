package com.hanghae.market.repository;

import com.hanghae.market.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByBoardIdAndUserId(Long board_id, Long user_id);
    List<Heart> findByBoardId(Long boardId);
}
