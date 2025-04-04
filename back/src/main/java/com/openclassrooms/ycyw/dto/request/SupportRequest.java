package com.openclassrooms.ycyw.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupportRequest {
    @NotNull(message = "subject is required.")
    private String subject;
    @NotNull(message = "content is required.")
    private String content;
    @NotNull(message = "sentAt is required.")
    private Date sentAt;
}
