package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.click_up.entity.Workspace;
import uz.pdp.click_up.entity.WorkspaceUser;

import java.util.List;
import java.util.Optional;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser,Long> {
    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id, Long user_id);

    @Transactional
    @Modifying
    void deleteByWorkspaceIdAndUserId(Long workspace_id, Long user_id);

    List<WorkspaceUser> findByWorkspaceId(Long workspace_id);
}
