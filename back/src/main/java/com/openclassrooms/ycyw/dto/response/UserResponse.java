package com.openclassrooms.ycyw.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing an User response.
 * <p>
 * This class is used to transfer user details in API responses.
 * </p>
 */
@Getter
@Builder
public class UserResponse {
	/**
	 * The unique identifier of the user.
	 */
	private Long id;
	/**
	 * The username of the user.
	 */
	private String username;
	/**
	 * The email of the user.
	 */
	private String email;
	/**
	 * The role of the user.
	 */
	private String role;
}
