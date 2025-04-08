package com.openclassrooms.ycyw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.request.SupportRequest;
import com.openclassrooms.ycyw.dto.response.ErrorResponse;
import com.openclassrooms.ycyw.dto.response.Response;
import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.model.Role;
import com.openclassrooms.ycyw.service.auth.AuthenticationService;
import com.openclassrooms.ycyw.service.command.SupportRequestCommandService;
import com.openclassrooms.ycyw.service.query.SupportQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Support", description = "User support")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token missing or invalid", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
})
@RestController
@RequestMapping("/supports")
public class SupportRestController {

    private final AuthenticationService authenticationService;
    private final SupportRequestCommandService supportRequestCommandService;
    private final SupportQueryService supportQueryService;

    SupportRestController(AuthenticationService authenticationService,
            SupportRequestCommandService supportRequestCommandService, SupportQueryService supportQueryService) {
        this.authenticationService = authenticationService;
        this.supportRequestCommandService = supportRequestCommandService;
        this.supportQueryService = supportQueryService;

    }

    @Operation(summary = "Support user request", description = "Create a new support request by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User request created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
    })
    @PostMapping("request")
    public ResponseEntity<Response> create(@Valid @RequestBody SupportRequest body) {
        Long userId = authenticationService.getAuthenticatedUserId();
        supportRequestCommandService.createRequest(userId, body);
        return ResponseEntity.ok()
                .body(Response.builder().message("User request created successfully.").build());
    }

    @Operation(summary = "Avaible users for support", description = "Return the avaible users according to current user role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users find successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
    })
    @GetMapping("/users/available")
    public ResponseEntity<List<UserResponse>> getAvailableUsers() {
        Role role = authenticationService.getAuthenticatedUserRole();
        List<UserResponse> users = supportQueryService.findAvailableUsersByRole(role);
        return ResponseEntity.ok(users);
    }

}
