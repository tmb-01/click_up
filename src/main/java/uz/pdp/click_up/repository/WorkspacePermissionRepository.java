package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.click_up.entity.WorkspacePermission;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission,Long> {
}
