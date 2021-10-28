package uz.pdp.click_up.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.click_up.entity.User;
import uz.pdp.click_up.entity.Workspace;
import uz.pdp.click_up.entity.WorkspaceUser;
import uz.pdp.click_up.entity.enums.WorkspacePermissionName;
import uz.pdp.click_up.entity.enums.WorkspaceRoleName;
import uz.pdp.click_up.payload.ApiResponse;
import uz.pdp.click_up.payload.MemberDto;
import uz.pdp.click_up.payload.WorkspaceDto;
import uz.pdp.click_up.security.CurrentUser;
import uz.pdp.click_up.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspace")
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?> addWorkspace(@RequestBody WorkspaceDto workspaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("{id}")
    public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.editWorkspace(id, workspaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("change-owner/{id}")
    public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id, @RequestParam Long ownerId, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(@RequestParam Long id, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("get-members-and-guests/{id}")
    public List<WorkspaceUser> getMembersAndGuests(@PathVariable Long id) {
        return workspaceService.getMembersAndGuests(id);
    }

    @GetMapping("get-workspace-list")
    public List<Workspace> getMyWorkspaces(@CurrentUser User user) {
        return workspaceService.getMyWorkspaces(user);
    }


    @PostMapping("add-role-to-workspace")
    public HttpEntity<?> addRoleToWorkspace(@RequestParam Long workspaceId, @RequestParam WorkspaceRoleName roleName, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addRoleToWorkspace(workspaceId, roleName, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("add-permission-to-workspace-role")
    public HttpEntity<?> addRoleToWorkspace(@RequestParam Long roleId, @RequestParam WorkspacePermissionName permissionName, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addPermissionToWorkspaceRole(roleId, permissionName, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("delete-role-of-workspace")
    public HttpEntity<?> addRoleToWorkspace(@RequestParam Long roleId, @RequestParam Long permissionId, @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.removePermissionOfWorkspaceRole(roleId, permissionId, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
