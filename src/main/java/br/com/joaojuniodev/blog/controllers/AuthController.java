package br.com.joaojuniodev.blog.controllers;

import br.com.joaojuniodev.blog.data.dto.security.AccountCredentialsDTO;
import br.com.joaojuniodev.blog.services.AuthService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/sign")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDTO credentials) {
        if (credentialsIsInvalid(credentials)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = service.signIn(credentials);

        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        return token;
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(
        @PathVariable("username") String username,
        @RequestHeader("Authorization") String refreshToken
    ) {
        if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = service.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(
        value = "/createUser",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE },
        consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    public AccountCredentialsDTO create(@RequestBody AccountCredentialsDTO credentials) {
        return service.create(credentials);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
            StringUtils.isBlank(credentials.getUsername()) ||
            StringUtils.isBlank(credentials.getPassword());
    }

}