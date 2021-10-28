package uz.pdp.click_up.service;

import org.springframework.stereotype.Service;
import uz.pdp.click_up.entity.User;
import uz.pdp.click_up.entity.Workspace;
import uz.pdp.click_up.entity.WorkspaceUser;
import uz.pdp.click_up.entity.enums.WorkspacePermissionName;
import uz.pdp.click_up.entity.enums.WorkspaceRoleName;
import uz.pdp.click_up.payload.ApiResponse;
import uz.pdp.click_up.payload.MemberDto;
import uz.pdp.click_up.payload.WorkspaceDto;

import java.util.List;
import java.util.UUID;

public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user);

    ApiResponse changeOwnerWorkspace(Long id, Long ownerId, User user);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto);

    ApiResponse joinToWorkspace(Long id, User user);

    List<WorkspaceUser> getMembersAndGuests(Long id);

    List<Workspace> getMyWorkspaces(User user);

    ApiResponse addRoleToWorkspace(Long workspaceId, WorkspaceRoleName roleName, User user);

    ApiResponse addPermissionToWorkspaceRole(Long roleId, WorkspacePermissionName workspacePermissionName, User user);

    ApiResponse removePermissionOfWorkspaceRole(Long roleId,Long permissionId,User user);
}
