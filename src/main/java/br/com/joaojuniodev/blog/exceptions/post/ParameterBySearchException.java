package br.com.joaojuniodev.blog.exceptions.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParameterBySearchException extends RuntimeException {
    public ParameterBySearchException(String message) {
        super(message);
    }
}
