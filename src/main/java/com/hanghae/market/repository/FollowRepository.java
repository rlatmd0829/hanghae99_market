package com.hanghae.market.repository;

import com.hanghae.market.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowUserId(Long follow_user_id);
}
