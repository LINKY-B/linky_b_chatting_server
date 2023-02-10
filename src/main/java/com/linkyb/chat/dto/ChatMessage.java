package com.linkyb.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    public enum MessageType { //메세지 타입 : 입장, 채팅, 퇴장
        ENTER, TALK, CLOSE
    }
    private MessageType type; // 메세지 타입
    private String roomId; // 방 번호
    private String sender; // 메세지 보낸 사람
    private String message; // 메세지
    private String sendingTime; // 메세지 전송 시간
}
