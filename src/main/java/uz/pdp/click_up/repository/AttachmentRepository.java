package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.click_up.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
