package com.linkyb.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkyb.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRepository{

    private final MongoTemplate mongoTemplate;

    public void save(ChatMessage message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        String messageJson = objectMapper.writeValueAsString(message);
        String roomId = message.getRoomId();
        mongoTemplate.insert(messageJson, roomId);
    }
}
