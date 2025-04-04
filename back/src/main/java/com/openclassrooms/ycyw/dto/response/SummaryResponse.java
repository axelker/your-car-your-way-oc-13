package com.openclassrooms.ycyw.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a summary response.
 * <p>
 * This class is used to return minimal details of an entity,
 * such as a user or a theme, without exposing full information.
 * </p>
 */
@Getter
@Builder
public class SummaryResponse {

    /**
     * The unique identifier of the entity.
     */
    private Long id;

    /**
     * The name associated with the entity.
     */
    private String name;
}
