package com.openclassrooms.ycyw.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupportMessageResponse {
    private String username;
    private String content;
    
}
