package com.hanghae.market.controller.chat;

import com.hanghae.market.dto.chat.ChatRoomDetailDto;
import com.hanghae.market.dto.chat.ChatRoomDto;
import com.hanghae.market.dto.chat.ChatRoomForm;
import com.hanghae.market.model.User;
import com.hanghae.market.model.chat.ChatMessage;
import com.hanghae.market.model.chat.ChatRoom;
import com.hanghae.market.model.chat.ChatRoomJoin;
import com.hanghae.market.service.UserService;
import com.hanghae.market.service.chat.ChatRoomJoinService;
import com.hanghae.market.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final UserService userService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;


    //항해톡 버튼 눌렀을때 -> room.html
    @GetMapping("/api/chat")
    @ResponseBody
    public Object chatHome(@RequestParam("email") String email) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.setSenderEmail(email);

        User user = userService.findUserByEmailMethod(email);
        List<ChatRoomJoin> chatRoomJoins = chatRoomJoinService.findByUser(user);
        List<ChatRoomForm> chatRooms = chatRoomService.setting(chatRoomJoins, user);

        chatRoomDto.setChatRooms(chatRooms);

        if (user == null) {
            chatRoomDto.setSenderName("");
            chatRoomDto.setSenderId(0L);
        } else {
            chatRoomDto.setSenderName(user.getUsername());
            chatRoomDto.setSenderId(user.getId());
        }
        return chatRoomDto;
    }


    //바로 채팅버튼 눌렀을 때 -> roomdetail.html
    @PostMapping("/api/chat/newChat")
    public String newChat(@RequestParam("receiver") String receiverEmail, @RequestParam("sender") String senderEmail) {//, RedirectAttributes redirectAttributes
        Long chatRoomId = chatRoomJoinService.newRoom(receiverEmail, senderEmail);
//        redirectAttributes.addAttribute("email",user2);
        return "redirect:/personalChat/?chatRoomId=" + chatRoomId + "&email=" + senderEmail;
//        return "redirect:/personalChat/" + chatRoomId;
    }

    @RequestMapping(value = {"/personalChat"})
    @ResponseBody
    public Object goChat(@RequestParam("chatRoomId") Long chatRoomId, @RequestParam("email") String senderEmail) {
//        String email = (String) request.getAttribute("userEmail");
        User userByEmailMethod = userService.findUserByEmailMethod(senderEmail);
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
            chatRoomDetailDto.setSenderName(userByEmailMethod.getUsername());
            chatRoomDetailDto.setSenderId(userByEmailMethod.getId());
        }
        List<ChatRoomJoin> list = chatRoomJoinService.findByChatRoom(chatRoom);
        chatRoomDetailDto.setMessages(messages);
        chatRoomDetailDto.setSenderEmail(userByEmailMethod.getEmail());
        chatRoomDetailDto.setChatRoomId(chatRoomId);
        int cnt = 0;
        for (ChatRoomJoin join : list) {
            if (!join.getUser().getUsername().equals(userByEmailMethod.getUsername())) {
                chatRoomDetailDto.setReceiverName(join.getUser().getUsername());
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

