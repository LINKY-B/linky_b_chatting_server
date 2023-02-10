package com.linkyb.chat.controller;

import com.linkyb.chat.dto.ChatMessage;
import com.linkyb.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatInfoController {

    private final ChatRepository repository;

    /**
     * 모든 대화 내용 출력
     */
    @GetMapping("/room/{id}/list")
    public List<ChatMessage> getMessages(@PathVariable String id) {
        return repository.getMessages(id);
    }
}
