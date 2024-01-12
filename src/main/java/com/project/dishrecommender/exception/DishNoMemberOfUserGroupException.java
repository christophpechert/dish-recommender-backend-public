package com.project.dishrecommender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DishNoMemberOfUserGroupException extends RuntimeException{

    public DishNoMemberOfUserGroupException(Long dishId) {
        super("Dish not a member of your UserGroup with id: " + dishId);
    }
}
