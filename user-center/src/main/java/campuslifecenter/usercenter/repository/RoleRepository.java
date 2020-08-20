package campuslifecenter.usercenter.repository;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<User> findUsersById(Long id);

}
