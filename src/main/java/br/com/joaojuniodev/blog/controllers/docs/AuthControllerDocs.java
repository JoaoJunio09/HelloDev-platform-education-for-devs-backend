package br.com.joaojuniodev.blog.controllers.docs;

import br.com.joaojuniodev.blog.data.dto.security.AccountCredentialsDTO;
import br.com.joaojuniodev.blog.mediatype.MediaTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthControllerDocs {

    @Operation(
        tags = {"Auth"},
        summary = "Performs user Login",
        description = "Method that performs user login to the system.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = {
                    @Content(mediaType = MediaTypes.APPLICATION_JSON),
                    @Content(mediaType = MediaTypes.APPLICATION_XML),
                    @Content(mediaType = MediaTypes.APPLICATION_YAML)}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    ResponseEntity<?> signIn(AccountCredentialsDTO credentials);

    @Operation(
        tags = {"Auth"},
        summary = "Updating token",
        description = "Method for updating the validation token.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = {
                    @Content(mediaType = MediaTypes.APPLICATION_JSON),
                    @Content(mediaType = MediaTypes.APPLICATION_XML),
                    @Content(mediaType = MediaTypes.APPLICATION_YAML)}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    ResponseEntity<?> refreshToken(String username, String refreshToken);

    @Operation(
        tags = {"Auth"},
        summary = "Creates the User",
        description = "Method to create a new user in the system.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success",
                content = {
                    @Content(mediaType = MediaTypes.APPLICATION_JSON),
                    @Content(mediaType = MediaTypes.APPLICATION_XML),
                    @Content(mediaType = MediaTypes.APPLICATION_YAML)}),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "200", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
        }
    )
    AccountCredentialsDTO create(AccountCredentialsDTO credentials);
}
