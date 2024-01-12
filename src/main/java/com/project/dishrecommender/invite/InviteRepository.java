package com.project.dishrecommender.invite;

import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.invite.entity.InviteStatus;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUserInfoIdAndStatus(Long userInfoId, InviteStatus status);

    List<Invite> findByUserGroupIdAndStatus(Long userGroupId, InviteStatus status);

    List<Invite> findByUserInfoIdAndUserGroupIdAndStatusOrderById(Long userInfoId, Long userGroupId, InviteStatus status);

}
