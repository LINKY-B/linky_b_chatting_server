package com.linkyb.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkyb.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ChatRepository {

    private final MongoTemplate mongoTemplate;

    public void save(ChatMessage message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();

        message.setSendingTime(format.format(time));
        String messageJson = objectMapper.writeValueAsString(message);
        String roomId = message.getRoomId();
        mongoTemplate.insert(messageJson, roomId);
    }

    public List<ChatMessage> getMessages(String id) {
        return mongoTemplate.findAll(ChatMessage.class, id);
    }
}
