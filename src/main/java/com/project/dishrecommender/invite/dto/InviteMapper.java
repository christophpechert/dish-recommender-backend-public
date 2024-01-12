package com.project.dishrecommender.invite.dto;

import com.project.dishrecommender.invite.entity.Invite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InviteMapper {

    InviteWithUserGroupDTO inviteToInviteWithUserGroupDTO(Invite invite);

    InviteWithUserInfoDTO inviteToInviteWithUserInfo(Invite invite);
}
