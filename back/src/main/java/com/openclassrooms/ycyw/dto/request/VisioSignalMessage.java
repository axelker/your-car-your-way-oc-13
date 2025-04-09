package com.openclassrooms.ycyw.dto.request;

import com.openclassrooms.ycyw.dto.enums.SignalingMessageType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisioSignalMessage {
    private SignalingMessageType type;
    private String payload;  // spd or iceCandidate
    private Long senderId;
    private Long receiverId;
}
