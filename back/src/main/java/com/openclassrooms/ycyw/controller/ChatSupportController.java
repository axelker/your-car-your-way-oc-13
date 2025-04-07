package com.openclassrooms.ycyw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.openclassrooms.ycyw.dto.request.ChatRequest;

@Controller
public class ChatSupportController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatRequest message) {
        messagingTemplate.convertAndSend("/topic/support/" + message.getReceiverId(), message);
    }
}
