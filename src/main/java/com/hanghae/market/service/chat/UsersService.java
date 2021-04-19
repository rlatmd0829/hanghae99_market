package com.hanghae.market.service.chat;

import com.hanghae.market.model.Users;
import com.hanghae.market.repository.chat.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;

    public Users findUserByEmailMethod(String userEmail) {
        return usersRepository.findUsersByEmail(userEmail);
    }
    public Users findByName(String sender) {
        return usersRepository.findByName(sender);
    }
}
