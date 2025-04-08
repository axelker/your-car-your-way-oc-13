package com.openclassrooms.ycyw.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ConversationRequest {
    @NotNull
    private Long participantId;
}
