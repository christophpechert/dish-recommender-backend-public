package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UserNoMemberException extends RuntimeException{

    public UserNoMemberException(Long userinfoId) {
        super("User not a member of User Group with ID: " + userinfoId);
    }

}
