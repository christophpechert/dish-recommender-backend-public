package com.project.dishrecommender.exception;

import com.project.dishrecommender.user.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistException extends RuntimeException{

    public EmailAlreadyExistException(UserInfo user) {
        super("User already exist with the name: " + user.getName());
    }
}
