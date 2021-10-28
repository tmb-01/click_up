package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.click_up.entity.Workspace;

import java.util.List;


public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByOwnerIdAndName(Long owner_id, String name);
    List<Workspace> findByOwnerId(Long owner_id);
}
