package com.hanghae.market.repository.chat;

import com.hanghae.market.model.User;
import com.hanghae.market.model.chat.ChatRoom;
import com.hanghae.market.model.chat.ChatRoomJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin,Long> {
    List<ChatRoomJoin> findByUser(User user);
    List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);
}
