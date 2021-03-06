package com.hanghae.market.model.chat;

import com.hanghae.market.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "join_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name =  "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public ChatRoomJoin(User user , ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
