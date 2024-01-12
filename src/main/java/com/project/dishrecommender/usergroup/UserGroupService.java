package com.project.dishrecommender.usergroup;

import com.project.dishrecommender.exception.UserAlreadyAssignToUserGroupException;
import com.project.dishrecommender.exception.UserGroupNotFoundException;
import com.project.dishrecommender.exception.UserNoAuthoritiesException;
import com.project.dishrecommender.exception.UserNoMemberException;
import com.project.dishrecommender.user.UserInfoService;
import com.project.dishrecommender.user.entity.UserInfo;
import com.project.dishrecommender.user.entity.UserRole;
import com.project.dishrecommender.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserGroupService {
    private final UserInfoService userInfoService;
    private final UserGroupRepository userGroupRepository;

    public UserGroup retrieveById(Long userGroupId) {
        return userGroupRepository.findById(userGroupId)
                .orElseThrow(() -> new UserGroupNotFoundException("Id: " + userGroupId));
    }

    public UserGroup retrieveFromUser() {
        UserInfo user = userInfoService.retrieveByAuth();

        if (user.getUserGroup() == null) {
            throw new UserNoMemberException(user.getId());
        }

        return retrieveById(user.getUserGroup().getId());
    }

    public UserGroup update(Long userGroupId, UserGroup userGroup) {
        UserGroup userGroupFromDb = retrieveById(userGroupId);

        userGroupFromDb.setName(userGroup.getName());
        userGroupFromDb.setLastChange(LocalDateTime.now());

        return userGroupRepository.save(userGroupFromDb);
    }

    public UserGroup add(UserGroup userGroup) {
        UserInfo user = userInfoService.retrieveByAuth();

        if (user.getUserGroup() != null) {
            throw new UserAlreadyAssignToUserGroupException(user);
        }
        user.setRole(UserRole.ROLE_OWNER);
        user.setUserGroup(userGroup);
        user.setLastChange(LocalDateTime.now());

        userGroup.getUserInfos().add(user);
        userGroup.setCreated(LocalDateTime.now());

        return userGroupRepository.save(userGroup);
    }

    public UserGroup removeByUserInfoId(Long userInfoId) throws UserNoAuthoritiesException, UserNoMemberException {
        UserGroup userGroup = retrieveFromUser();
        UserInfo authenticatedUser = userInfoService.retrieveByAuth();

        userGroup.getUserInfos()
                .stream()
                .filter(e -> e.getId().equals(authenticatedUser.getId()))
                .findAny()
                .orElseThrow(() -> new UserNoMemberException(userInfoId));

        UserInfo userToRemove = userGroup.getUserInfos()
                .stream()
                .filter(e -> e.getId().equals(userInfoId))
                .findFirst()
                .orElseThrow(() -> new UserNoMemberException(userInfoId));

        if (!authenticatedUser.getId().equals(userToRemove.getId())) {
            if (authenticatedUser.getRole() != UserRole.ROLE_OWNER || userToRemove.getRole() == UserRole.ROLE_OWNER) {
                throw new UserNoAuthoritiesException(authenticatedUser.getId());
            }
        }

        userToRemove.setUserGroup(null);
        userToRemove.setRole(UserRole.ROLE_NEWBIE);
        userToRemove.setLastChange(LocalDateTime.now());
        userInfoService.save(userToRemove);

        userGroup.getUserInfos().removeIf(e -> e.getId().equals(userToRemove.getId()));
        userGroup.setLastChange(LocalDateTime.now());

        return userGroupRepository.save(userGroup);
    }

    public List<UserGroup> retrieveBySearch(String searchWord) {
        return userGroupRepository.findAllByNameIgnoreCaseContainingOrderByName(searchWord);
    }

    public UserGroup addUser(Long userGroupId, UserInfo user, UserRole role) {
        UserGroup userGroup = retrieveById(userGroupId);

        if (user.getUserGroup() != null && user.getUserGroup().getId().equals(userGroupId)) {
            throw new UserAlreadyAssignToUserGroupException(user);
        }

        userGroup.getUserInfos().add(user);
        userGroup.setLastChange(LocalDateTime.now());
        userGroupRepository.save(userGroup);

        user.setUserGroup(userGroup);
        user.setRole(role);
        user.setLastChange(LocalDateTime.now());

        userInfoService.save(user);

        return userGroup;
    }
}
