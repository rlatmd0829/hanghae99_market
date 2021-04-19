package com.hanghae.market.controller.chat;

import com.hanghae.market.dto.chat.ChatRoomDetailDto;
import com.hanghae.market.dto.chat.ChatRoomDto;
import com.hanghae.market.dto.chat.ChatRoomForm;
import com.hanghae.market.model.Users;
import com.hanghae.market.model.chat.ChatMessage;
import com.hanghae.market.model.chat.ChatRoom;
import com.hanghae.market.model.chat.ChatRoomJoin;
import com.hanghae.market.service.chat.ChatRoomJoinService;
import com.hanghae.market.service.chat.ChatRoomService;
import com.hanghae.market.service.chat.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final UsersService usersService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;


    //항해톡 버튼 눌렀을때 -> room.html
    @GetMapping("/api/chat")
    @ResponseBody
    public Object chatHome(@RequestParam("email") String email) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setSenderEmail(email);

        Users user = usersService.findUserByEmailMethod(email);
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(user);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, user);

        chatRoomDto.setChatRooms(chatRooms);

        if (user == null) {
            chatRoomDto.setSenderName("");
            chatRoomDto.setSenderId(0L);
        } else {
            chatRoomDto.setSenderName(user.getName());
            chatRoomDto.setSenderId(user.getId());
        }
        return chatRoomDto;
    }


    //바로 채팅버튼 눌렀을 때 -> roomdetail.html
    @PostMapping("/api/chat/newChat")
    @ResponseBody
    public String newChat(@RequestParam("receiver") String receiverEmail, @RequestParam("sender") String senderEmail) {//, RedirectAttributes redirectAttributes
        Long chatRoomId = chatRoomJoinService.newRoom(receiverEmail, senderEmail);
//        redirectAttributes.addAttribute("email",user2);
        return "redirect:/personalChat/?chatRoomId=" + chatRoomId + "&email=" + senderEmail;
//        return "redirect:/personalChat/" + chatRoomId;
    }

    @RequestMapping(value = {"/personalChat", "/personalChat"})
    public Object goChat(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("email") String senderEmail) {
//        String email = (String) request.getAttribute("userEmail");
        Users userByEmailMethod = usersService.findUserByEmailMethod(senderEmail);
        Optional<ChatRoom> opt = chatRoomService.findById(chatRoomId);
        ChatRoom chatRoom = opt.get();
        List<ChatMessage> messages = chatRoom.getMessages();
        Collections.sort(messages, (t1, t2) -> {
            if (t1.getId() > t2.getId()) return -1;
            else return 1;
        });

        ChatRoomDetailDto chatRoomDetailDto = new ChatRoomDetailDto();

        if (userByEmailMethod == null) {
            chatRoomDetailDto.setSenderName("");
            chatRoomDetailDto.setSenderId(0L);
        } else {
            chatRoomDetailDto.setSenderName(userByEmailMethod.getName());
            chatRoomDetailDto.setSenderId(userByEmailMethod.getId());
        }
        List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
        chatRoomDetailDto.setMessages(messages);
        chatRoomDetailDto.setSenderEmail(userByEmailMethod.getEmail());
        chatRoomDetailDto.setChatRoomId(chatRoomId);
        int cnt = 0;
        for (ChatRoomJoin join : list) {
            if (!join.getUser().getName().equals(userByEmailMethod.getName())) {
                chatRoomDetailDto.setReceiverName(join.getUser().getName());
                ++cnt;
            }
        }
        if (cnt >= 2) {
            return "redirect:/api/chat";
        }
        if (cnt == 0) {
            chatRoomDetailDto.setReceiverName("");
        }
        return chatRoomDetailDto;
    }


}

