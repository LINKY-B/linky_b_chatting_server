package com.linkyb.chat.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkyb.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ChatRepository {

    private final MongoTemplate mongoTemplate;

    public void save(ChatMessage message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Date time = new Date();

        message.setSendingTime(time.toString());
        String messageJson = objectMapper.writeValueAsString(message);
        String roomId = message.getRoomId();
        mongoTemplate.insert(messageJson, roomId);
    }

    public List<ChatMessage> getMessages(String id) {
        return mongoTemplate.findAll(ChatMessage.class, id);
    }

    public List<ChatMessage> getMessagesInfo(List<String> values) {

        ObjectMapper mapper = new ObjectMapper();
        List<ChatMessage> chatMessages = new ArrayList<>();

        Query query = new Query();
        query.limit(1);
        query.with(Sort.by(Sort.Direction.DESC,"sendingTime"));

        for (String i:values) {
            List<ChatMessage> tempMessage = mongoTemplate.find(query, ChatMessage.class, i);
            chatMessages.add(tempMessage.get(0));
        }

        chatMessages.sort(Comparator.comparing(ChatMessage::getSendingTime).reversed());

        return chatMessages;
    }
}
