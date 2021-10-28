package uz.pdp.click_up.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.click_up.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
