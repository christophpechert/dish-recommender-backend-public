package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNoAuthoritiesException extends RuntimeException{

    public UserNoAuthoritiesException(Long userinfoId) {
        super("User has not the necessary authorities for this action, with ID: " + userinfoId);
    }

}
