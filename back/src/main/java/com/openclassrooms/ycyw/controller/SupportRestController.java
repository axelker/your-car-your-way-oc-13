package com.openclassrooms.ycyw.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.request.AuthLoginRequest;
import com.openclassrooms.ycyw.dto.request.SupportRequest;
import com.openclassrooms.ycyw.dto.request.UserRequest;
import com.openclassrooms.ycyw.dto.response.ErrorResponse;
import com.openclassrooms.ycyw.dto.response.Response;
import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.service.auth.AuthenticationService;
import com.openclassrooms.ycyw.service.command.SupportRequestCommandService;
import com.openclassrooms.ycyw.service.command.UserCommandeService;
import com.openclassrooms.ycyw.service.query.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Suport", description = "User support")
@ApiResponses({
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
})
@RestController
@RequestMapping("/support")
public class SupportRestController {

    private final AuthenticationService authenticationService;
    private final SupportRequestCommandService supportRequestCommandService;

    SupportRestController(AuthenticationService authenticationService,SupportRequestCommandService supportRequestCommandService) {
        this.authenticationService = authenticationService;
        this.supportRequestCommandService = supportRequestCommandService;
    }


    @Operation(summary = "Support user request", description = "Create a new support request by the user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User request created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
    })
    @PostMapping("request")
    public ResponseEntity<Response> login(@Valid @RequestBody SupportRequest body) {
        Long userId = authenticationService.getAuthenticatedUserId();
        supportRequestCommandService.createRequest(userId,body);
        return ResponseEntity.ok()
                        .body(Response.builder().message("User request created successfully.").build());
    }

}
