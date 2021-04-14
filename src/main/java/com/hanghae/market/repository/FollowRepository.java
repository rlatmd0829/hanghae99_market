package com.hanghae.market.repository;

import com.hanghae.market.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowUserIdAndUserId(Long follow_user_id, Long user_id); // FollowUserId, UserId 외래키, 데이터베이스 에서는 스네이크케이스 방식으로 표현됨

    List<Follow> findByFollowUserId(Long followUserId);

}
