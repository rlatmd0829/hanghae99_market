package com.hanghae.market.repository;

import com.hanghae.market.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();

    List<Board> findByTitleContainingOrContentContaining(String title, String title1);
}
