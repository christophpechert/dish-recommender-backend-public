package com.project.dishrecommender.usergroup.dto;

import com.project.dishrecommender.user.entity.UserInfo;

import java.util.List;

public record UserGroupWithUserInfosDTO(
        Long id,
        String name,
        List<UserInfo> userInfos) {
}
