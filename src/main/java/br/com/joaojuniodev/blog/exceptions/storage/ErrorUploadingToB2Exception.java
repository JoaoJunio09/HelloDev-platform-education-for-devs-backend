package br.com.joaojuniodev.blog.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ErrorUploadingToB2Exception extends RuntimeException {
    public ErrorUploadingToB2Exception(String message) {
        super(message);
    }
}
