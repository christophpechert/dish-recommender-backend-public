package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserGroupNotFoundException extends RuntimeException{

    public UserGroupNotFoundException(String message) {
        super(message);
    }

    public UserGroupNotFoundException() {
        super();
    }
}
