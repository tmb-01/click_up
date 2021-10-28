package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.click_up.entity.WorkspaceRole;

import java.util.Optional;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole,Long> {
    Optional<WorkspaceRole> findByNameAndWorkspaceId(String name, Long workspace_id);
}
