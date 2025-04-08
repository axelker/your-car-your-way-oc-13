package com.openclassrooms.ycyw.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationResponse {
    private Long id;
    private String name;
    private List<SupportMessageResponse> messages;
}
