package uz.pdp.click_up.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.click_up.entity.*;
import uz.pdp.click_up.entity.enums.AddType;
import uz.pdp.click_up.entity.enums.WorkspacePermissionName;
import uz.pdp.click_up.entity.enums.WorkspaceRoleName;
import uz.pdp.click_up.payload.ApiResponse;
import uz.pdp.click_up.payload.MemberDto;
import uz.pdp.click_up.payload.WorkspaceDto;
import uz.pdp.click_up.repository.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user) {

        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDto.getName())) {
            return new ApiResponse("you have this kind of workspace", false);
        }

        Workspace workspace = new Workspace();
        workspace.setName(workspaceDto.getName());
        workspace.setColor(workspaceDto.getColor());
        workspace.setOwner(user);
        workspace.setInitialLetter(workspaceDto.getName().substring(0, 1));
        workspace.setAvatar(workspaceDto.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDto.getAvatarId()).get());

        Workspace savedWorkspace = workspaceRepository.save(workspace);

        WorkspaceRole ownerRole = new WorkspaceRole();
        ownerRole.setWorkspace(workspace);
        ownerRole.setName(WorkspaceRoleName.ROLE_OWNER.name());
        ownerRole = workspaceRoleRepository.save(ownerRole);

        WorkspaceRole adminRole = new WorkspaceRole();
        adminRole.setWorkspace(workspace);
        adminRole.setName(WorkspaceRoleName.ROLE_ADMIN.name());
        adminRole = workspaceRoleRepository.save(adminRole);

        WorkspaceRole memberRole = new WorkspaceRole();
        memberRole.setWorkspace(workspace);
        memberRole.setName(WorkspaceRoleName.ROLE_MEMBER.name());
        memberRole = workspaceRoleRepository.save(memberRole);

        WorkspaceRole guestRole = new WorkspaceRole();
        guestRole.setWorkspace(workspace);
        guestRole.setName(WorkspaceRoleName.ROLE_GUEST.name());
        guestRole = workspaceRoleRepository.save(guestRole);

        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();

        List<WorkspacePermission> workspacePermissionList = new ArrayList<>();
        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission workspacePermission = new WorkspacePermission();
            workspacePermission.setWorkspaceRole(ownerRole);
            workspacePermission.setPermission(workspacePermissionName);
            workspacePermissionList.add(workspacePermission);

            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {

                workspacePermission = new WorkspacePermission();
                workspacePermission.setWorkspaceRole(adminRole);
                workspacePermission.setPermission(workspacePermissionName);
                workspacePermissionList.add(workspacePermission);
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {

                workspacePermission = new WorkspacePermission();
                workspacePermission.setWorkspaceRole(memberRole);
                workspacePermission.setPermission(workspacePermissionName);
                workspacePermissionList.add(workspacePermission);
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {

                workspacePermission = new WorkspacePermission();
                workspacePermission.setWorkspaceRole(guestRole);
                workspacePermission.setPermission(workspacePermissionName);
                workspacePermissionList.add(workspacePermission);
            }
        }

        workspacePermissionRepository.saveAll(workspacePermissionList);


        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setWorkspace(savedWorkspace);
        workspaceUser.setUser(user);
        workspaceUser.setWorkspaceRole(ownerRole);
        workspaceUser.setDateJoined(Timestamp.valueOf(LocalDateTime.now()));
        workspaceUser.setDateInvited(Timestamp.valueOf(LocalDateTime.now()));

        workspaceUserRepository.save(workspaceUser);

        return new ApiResponse("workspace saved", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
        if (workspaceUser.getWorkspaceRole().getName().equals(WorkspaceRoleName.ROLE_OWNER.name()) ||
                workspaceUser.getWorkspaceRole().getName().equals(WorkspaceRoleName.ROLE_ADMIN.name())) {
            if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDto.getName())) {
                return new ApiResponse("you have this kind of workspace", false);
            }
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(workspaceDto.getAvatarId());
            Attachment attachment = optionalAttachment.get();
            Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
            if (optionalWorkspace.isPresent()) {
                Workspace workspace = optionalWorkspace.get();
                workspace.setName(workspaceDto.getName());
                workspace.setColor(workspaceDto.getColor());
                workspace.setAvatar(attachment);
                workspaceRepository.save(workspace);
                return new ApiResponse("workspace updated", true);
            }
            return new ApiResponse("workspace is not exist", false);
        }
        return new ApiResponse("you can't update this workspace", false);
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, Long ownerId, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();

            if (workspaceUser.getWorkspaceRole().getName().equals(WorkspaceRoleName.ROLE_OWNER.name())) {

                Optional<WorkspaceUser> optionalWorkspaceUser1 = workspaceUserRepository.findByWorkspaceIdAndUserId(id, ownerId);
                if (optionalWorkspaceUser1.isPresent()) {

                    Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findByNameAndWorkspaceId(WorkspaceRoleName.ROLE_OWNER.name(), id);

                    if (optionalWorkspaceRole.isPresent()) {
                        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
                        WorkspaceUser workspaceUser1 = optionalWorkspaceUser1.get();
                        workspaceUser1.setWorkspaceRole(workspaceRole);
                        workspaceUserRepository.save(workspaceUser1);

                        return new ApiResponse("owner changed", true);
                    }
                    return new ApiResponse("role not found", false);
                }
                return new ApiResponse("user not found", false);
            }
            return new ApiResponse("you are not owner", false);
        }
        return new ApiResponse("you are not in this workspace", false);
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("workspace deleted", true);
        } catch (Exception e) {
            return new ApiResponse("error", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDto memberDto) {

        if (memberDto.getAddType() == AddType.ADD) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new RuntimeException("not found")),
                    userRepository.findById(memberDto.getMemberId()).orElseThrow(() -> new RuntimeException("not found")),
                    workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new RuntimeException("not found")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDto.getAddType() == AddType.EDIT) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDto.getMemberId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new RuntimeException("not found")));


        } else if (memberDto.getAddType() == AddType.REMOVE) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDto.getMemberId());
        }
        return new ApiResponse("Success", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("success", true);
        }
        return new ApiResponse("error", false);
    }

    @Override
    public List<WorkspaceUser> getMembersAndGuests(Long id) {
        return workspaceUserRepository.findByWorkspaceId(id);
    }

    @Override
    public List<Workspace> getMyWorkspaces(User user) {
        return workspaceRepository.findByOwnerId(user.getId());
    }

    @Override
    public ApiResponse addRoleToWorkspace(Long workspaceId, WorkspaceRoleName roleName, User user) {

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);

        if (optionalWorkspace.isPresent()) {

            Workspace workspace = optionalWorkspace.get();
            if (workspace.getOwner().getId().equals(user.getId())) {
                WorkspaceRole workspaceRole = new WorkspaceRole();
                workspaceRole.setWorkspace(workspace);
                workspaceRole.setName(roleName.name());
                workspaceRoleRepository.save(workspaceRole);
                return new ApiResponse("role saved", true);
            }
            return new ApiResponse("you can't add role", false);
        }
        return new ApiResponse("workspace is not found", false);
    }

    @Override
    public ApiResponse addPermissionToWorkspaceRole(Long roleId, WorkspacePermissionName workspacePermissionName, User user) {
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(roleId);
        if (optionalWorkspaceRole.isPresent()) {
            WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
            if (workspaceRole.getWorkspace().getOwner().getId().equals(user.getId())) {
                WorkspacePermission workspacePermission = new WorkspacePermission();
                workspacePermission.setPermission(workspacePermissionName);
                workspacePermission.setWorkspaceRole(workspaceRole);
                workspacePermissionRepository.save(workspacePermission);
                return new ApiResponse("permission saved", true);
            }
            return new ApiResponse("you can't add permission", false);
        }
        return new ApiResponse("role is not found", false);
    }

    @Override
    public ApiResponse removePermissionOfWorkspaceRole(Long roleId,Long permissionId,User user) {
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(roleId);
        if (optionalWorkspaceRole.isPresent()) {
            WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
            if (workspaceRole.getWorkspace().getOwner().getId().equals(user.getId())) {
                workspacePermissionRepository.deleteById(permissionId);
                return new ApiResponse("permission deleted", true);
            }
            return new ApiResponse("you can't delete permission", false);
        }
        return new ApiResponse("role is not found", false);
    }


}
