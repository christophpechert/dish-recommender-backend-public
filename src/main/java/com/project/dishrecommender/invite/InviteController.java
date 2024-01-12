package com.project.dishrecommender.invite;

import com.project.dishrecommender.invite.dto.InviteWithUserInfoDTO;
import com.project.dishrecommender.invite.entity.Invite;
import com.project.dishrecommender.invite.dto.InviteMapper;
import com.project.dishrecommender.invite.dto.InviteWithUserGroupDTO;
import com.project.dishrecommender.invite.entity.InviteStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("invite-management")
public class InviteController {
    private final InviteService inviteService;
    private final InviteMapper inviteMapper;

    @PostMapping("/invite/user-group/{userGroupId}")
    ResponseEntity<InviteWithUserGroupDTO> addToUserGroup(@PathVariable Long userGroupId, @RequestBody Invite invite) {
        InviteWithUserGroupDTO inviteDTO = inviteMapper.inviteToInviteWithUserGroupDTO(inviteService.addToUserGroup(userGroupId, invite));
        return new ResponseEntity<>(inviteDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_GUEST')")
    @GetMapping("/invite/user-group/{userGroupId}")
    ResponseEntity<List<InviteWithUserInfoDTO>> retrieveAllActiveFromUserGroupById(@PathVariable Long userGroupId) {
        List<InviteWithUserInfoDTO> invites = inviteService.retrieveAllActiveFromUserGroupById(userGroupId)
                .stream()
                .map(inviteMapper::inviteToInviteWithUserInfo)
                .toList();
        return new ResponseEntity<>(invites, HttpStatus.OK);
    }

    @PutMapping("/invite/{inviteId}/retract")
    ResponseEntity<Invite> retract(@PathVariable Long inviteId) {
        return new ResponseEntity<>(inviteService.changeState(inviteId, InviteStatus.RETRACT), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/invite/{inviteId}/accept")
    ResponseEntity<InviteWithUserInfoDTO> accept(@PathVariable Long inviteId) {
        InviteWithUserInfoDTO invite = inviteMapper.inviteToInviteWithUserInfo(inviteService.acceptInvite(inviteId));
        return new ResponseEntity<>(invite, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PutMapping("/invite/{inviteId}/reject")
    ResponseEntity<InviteWithUserInfoDTO> reject(@PathVariable Long inviteId) {
        InviteWithUserInfoDTO invite = inviteMapper.inviteToInviteWithUserInfo(inviteService.changeState(inviteId, InviteStatus.REJECTED));
        return new ResponseEntity<>(invite, HttpStatus.OK);
    }

    @GetMapping("/invite/user")
    ResponseEntity<List<InviteWithUserGroupDTO>> retrieveAllActiveFromUser() {
        List<InviteWithUserGroupDTO> invites = inviteService.retrieveAllActiveFromUser()
                .stream()
                .map(inviteMapper::inviteToInviteWithUserGroupDTO)
                .toList();
        return new ResponseEntity<>(invites, HttpStatus.OK);
    }
}
