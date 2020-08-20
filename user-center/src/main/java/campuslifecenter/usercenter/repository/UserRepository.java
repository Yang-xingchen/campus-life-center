package campuslifecenter.usercenter.repository;

import campuslifecenter.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySingInId(Long singInId);
}
