package br.com.joaojuniodev.blog.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileIdDoesNotMatchPostIdException extends RuntimeException {
    public FileIdDoesNotMatchPostIdException(String message) {
        super(message);
    }
}
