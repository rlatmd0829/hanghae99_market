package com.hanghae.market.repository;


import com.hanghae.market.model.User;
import com.hanghae.market.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    User findByKakaoId(String kakaoid);
}