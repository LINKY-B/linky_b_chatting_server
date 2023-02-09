package com.linkyb.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkyb.chat.dto.ChatMessage;
import com.linkyb.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRepository chatRepository;

    @MessageMapping("/chat/message") // /pub/chat/message - 메세지 발행
    public void message(ChatMessage message) throws JsonProcessingException {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
        if (ChatMessage.MessageType.CLOSE.equals(message.getType())) {
            message.setMessage("상대방이 채팅방을 나갔습니다.");
        }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message); // /sub/chat/room/1 - 구독

        chatRepository.save(message);
    }
}
