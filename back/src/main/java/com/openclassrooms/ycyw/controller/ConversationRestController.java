package com.openclassrooms.ycyw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.request.ConversationRequest;
import com.openclassrooms.ycyw.dto.response.ConversationResponse;
import com.openclassrooms.ycyw.dto.response.ErrorResponse;

import com.openclassrooms.ycyw.service.auth.AuthenticationService;
import com.openclassrooms.ycyw.service.command.ConversationCommandService;
import com.openclassrooms.ycyw.service.query.ConversationQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "conversation", description = "User conversation")
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Unauthorized - Token missing or invalid", content = @Content),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
})
@RestController
@RequestMapping("/conversations")
public class ConversationRestController {
    

    private final AuthenticationService authenticationService;
    private final ConversationCommandService conversationCommandService;
    private final ConversationQueryService conversationQueryService;


    ConversationRestController(AuthenticationService authenticationService,
            ConversationCommandService conversationCommandService,
            ConversationQueryService conversationQueryService) {
        this.authenticationService = authenticationService;
        this.conversationCommandService = conversationCommandService;
        this.conversationQueryService = conversationQueryService;

    }

    @Operation(summary = "Find a conversation by its ID.", description = "Returns one conversation with messages.")
    @ApiResponse(responseCode = "200", description = "Conversation found successfully", 
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        schema = @Schema(implementation = ConversationResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ConversationResponse> getConversation(@PathVariable Long id) {
        return ResponseEntity.ok(conversationQueryService.findById(id));
    }

    @Operation(summary = "Find all conversations of a logged user.", description = "Returns all conversations that the user is participating in.")
    @ApiResponse(responseCode = "200", description = "Conversations found successfully",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
        array = @ArraySchema(schema = @Schema(implementation = ConversationResponse.class))))
    @GetMapping("")
    public ResponseEntity<List<ConversationResponse>> getAllConversationsByUser() {
        Long currentUserId = authenticationService.getAuthenticatedUserId();
        return ResponseEntity.ok(conversationQueryService.findAllByUser(currentUserId));
    }

    @Operation(summary = "Create conversation.", description = "Create a conversation with the current and participate user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Users conversation find successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConversationResponse.class))),
    })
    @PostMapping("")
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody ConversationRequest request) {
        Long currentUserId = authenticationService.getAuthenticatedUserId();
        ConversationResponse res = conversationCommandService.startConversationBetweenTwoUser(currentUserId, request.getParticipantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
