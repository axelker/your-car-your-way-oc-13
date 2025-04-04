package com.openclassrooms.ycyw.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a generic response message.
 * <p>
 * This class is used to return standard API responses containing a message.
 * </p>
 */
@Getter
@Builder
public class Response {

    /**
     * The response message providing feedback on the operation.
     */
    private String message;
}
