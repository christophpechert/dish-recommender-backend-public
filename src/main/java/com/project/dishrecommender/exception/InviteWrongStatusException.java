package com.project.dishrecommender.exception;

import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.invite.entity.InviteStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InviteWrongStatusException extends RuntimeException{

    public InviteWrongStatusException(Invite invite) {
        super("Wrong status for invite with ID: " + invite.getId());
    }
}
