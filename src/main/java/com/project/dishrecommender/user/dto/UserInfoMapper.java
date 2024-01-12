package com.project.dishrecommender.user.dto;

import com.project.dishrecommender.user.entity.UserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfoWithUserGroupDTO userInfoToUserInfoWithUserGroupDTO(UserInfo userInfo);
}
