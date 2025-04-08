package com.openclassrooms.ycyw.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequest {
    @NotNull
    private Long senderId;
    @NotNull
    private Long conversationId;
    @NotNull
    private String content;
}
