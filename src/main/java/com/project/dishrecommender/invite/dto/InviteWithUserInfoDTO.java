package com.project.dishrecommender.invite.dto;

import com.project.dishrecommender.invite.entity.InviteStatus;
import com.project.dishrecommender.user.dto.UserInfoDTO;
import com.project.dishrecommender.usergroup.entity.UserGroup;

import java.time.LocalDateTime;

public record InviteWithUserInfoDTO(
        Long id,
        String message,
        LocalDateTime crated,
        LocalDateTime lastChange,
        InviteStatus status,
        UserInfoDTO userInfo
) {
}
