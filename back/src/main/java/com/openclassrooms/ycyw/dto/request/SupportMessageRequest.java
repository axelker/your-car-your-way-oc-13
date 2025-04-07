package com.openclassrooms.ycyw.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupportMessageRequest {
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotNull
    private String content;
}
