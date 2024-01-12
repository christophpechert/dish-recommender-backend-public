package com.project.dishrecommender.usergroup.dto;

import com.project.dishrecommender.usergroup.entity.UserGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {

    UserGroupWithMenusAndKeywordsDTO ToUserGroupWithMenusAndKeywords(UserGroup userGroup);

    UserGroupWithUserInfosDTO toUserGroupWithUsers(UserGroup userGroup);
}
