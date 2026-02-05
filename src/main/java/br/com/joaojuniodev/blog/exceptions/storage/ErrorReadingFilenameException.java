package br.com.joaojuniodev.blog.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ErrorReadingFilenameException extends RuntimeException {
    public ErrorReadingFilenameException(String message) {
        super(message);
    }
}
