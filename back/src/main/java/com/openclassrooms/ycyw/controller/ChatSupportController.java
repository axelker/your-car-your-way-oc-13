package com.openclassrooms.ycyw.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.openclassrooms.ycyw.dto.request.SupportMessageRequest;
import com.openclassrooms.ycyw.service.command.SupportMessageCommandService;

@Controller
public class ChatSupportController {
    private final SupportMessageCommandService supportMessageCommandService;
    public ChatSupportController(SupportMessageCommandService supportMessageCommandService) {
        this.supportMessageCommandService = supportMessageCommandService;
    }

    @MessageMapping("/chat.send")
    public void handleMessage(@Payload SupportMessageRequest request) {
        supportMessageCommandService.handleAndBroadcastMessage(request);
    }
}
