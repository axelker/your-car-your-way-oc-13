package com.openclassrooms.ycyw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.openclassrooms.ycyw.dto.request.VisioSignalMessage;

@Controller
public class VisioSupportSignalController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/visio.send")
    public void sendSignal(@Payload VisioSignalMessage message) {
        messagingTemplate.convertAndSend("/topic/visio/" + message.getReceiverId(), message);
    }
}