package campuslifecenter.usercenter.repository;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<Role> findRoleById(Long id);

    String findPasswordById(Long id);
}
