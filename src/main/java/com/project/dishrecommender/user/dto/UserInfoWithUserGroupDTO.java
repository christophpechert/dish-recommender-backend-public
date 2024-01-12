package com.project.dishrecommender.user.dto;

import com.project.dishrecommender.user.entity.UserRole;
import com.project.dishrecommender.usergroup.entity.UserGroup;

import java.time.LocalDateTime;

public record UserInfoWithUserGroupDTO(
        Long id,
        String name,
        String email,
        UserRole role,
        LocalDateTime created,
        LocalDateTime lastChange,
        UserGroup userGroup) {
}
