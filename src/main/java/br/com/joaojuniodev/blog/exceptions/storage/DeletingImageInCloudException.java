package br.com.joaojuniodev.blog.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DeletingImageInCloudException extends RuntimeException {
    public DeletingImageInCloudException(String message) {
        super(message);
    }
}
