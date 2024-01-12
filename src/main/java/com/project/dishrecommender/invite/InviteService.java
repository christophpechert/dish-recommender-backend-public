package com.project.dishrecommender.invite;

import com.project.dishrecommender.exception.InviteAlreadyExistException;
import com.project.dishrecommender.exception.InviteNotFoundException;
import com.project.dishrecommender.exception.InviteWrongStatusException;
import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.invite.entity.InviteStatus;
import com.project.dishrecommender.user.UserInfoService;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.user.entity.UserRole;
import com.project.dishrecommender.usergroup.UserGroupService;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserInfoService userInfoService;
    private final UserGroupService userGroupService;

    @Transactional
    public Invite addToUserGroup(Long userGroupId, Invite invite) {
        UserInfo user = userInfoService.retrieveByAuth();
        UserGroup userGroup = userGroupService.retrieveById(userGroupId);

        if (userGroup.getUserInfos().isEmpty()) {
            userGroupService.addUser(userGroup.getId(), user, UserRole.ROLE_OWNER);
            invite.setStatus(InviteStatus.ACCEPTED);
        }  else {
            List<Invite> invites = inviteRepository.findByUserInfoIdAndUserGroupIdAndStatusOrderById(user.getId(), userGroup.getId(), InviteStatus.OPEN);
            if (!invites.isEmpty()) {
                throw new InviteAlreadyExistException(invites.get(0));
            }
            invite.setStatus(InviteStatus.OPEN);
        }
        invite.setCreated(LocalDateTime.now());
        invite.setUserInfo(user);
        invite.setUserGroup(userGroup);

        return save(invite);
    }

    public List<Invite> retrieveAllActiveFromUser() {
        UserInfo user = userInfoService.retrieveByAuth();
        return inviteRepository.findByUserInfoIdAndStatus(user.getId(), InviteStatus.OPEN);
    }

    public List<Invite> retrieveAllActiveFromUserGroupById(Long userGroupId) {
        UserGroup userGroup = userGroupService.retrieveById(userGroupId);
        return inviteRepository.findByUserGroupIdAndStatus(userGroup.getId(), InviteStatus.OPEN);
    }

    public Invite changeState(Long inviteId, InviteStatus inviteStatus) {
        Invite invite = retrieveById(inviteId);

        if (invite.getStatus() != InviteStatus.OPEN) {
            throw new InviteWrongStatusException(invite);
        }

        invite.setStatus(inviteStatus);
        invite.setLastChange(LocalDateTime.now());

        return save(invite);
    }

    public Invite retrieveById(Long inviteId) {
        return inviteRepository.findById(inviteId)
                .orElseThrow(() -> new InviteNotFoundException(inviteId));
    }

    @Transactional
    public Invite acceptInvite(Long inviteId) {
        Invite invite = retrieveById(inviteId);

        if (invite.getStatus() != InviteStatus.OPEN) {
            throw new InviteWrongStatusException(invite);
        }

        UserInfo user = userInfoService.retrieveByAuth();

        if (!invite.getUserGroup().getId().equals(user.getUserGroup().getId())) {
            throw new InviteNotFoundException(inviteId);
        }

        invite.setStatus(InviteStatus.ACCEPTED);
        invite.setLastChange(LocalDateTime.now());
        inviteRepository.save(invite);

        userGroupService.addUser(invite.getUserGroup().getId(), invite.getUserInfo(), UserRole.ROLE_GUEST);
        return invite;
    }

    private Invite save(Invite invite) {
        return inviteRepository.save(invite);
    }
}
