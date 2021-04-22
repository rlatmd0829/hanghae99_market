package com.hanghae.market.repository;


import com.hanghae.market.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByUsername(String username);
    User findByKakaoId(String kakaoid);

    boolean existsByEmail(String userEmail);
    User findUsersByEmail(String userEmail);
    User findByUsername(String sender);

}