package com.hanghae.market.repository.chat;

import com.hanghae.market.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String userEmail);
    Users findUsersByEmail(String userEmail);

    Users findByName(String sender);
}