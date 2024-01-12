package com.project.dishrecommender.exception;

import com.project.dishrecommender.user.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyAssignToUserGroupException extends RuntimeException{

    public UserAlreadyAssignToUserGroupException(UserInfo user) {
        super("User already assign to UserGroup, User ID: " + user.getId());
    }

}
