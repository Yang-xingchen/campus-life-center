package campuslifecenter.usercenter.repository;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<User> findUsersById(Long id);

    RoleInfo getInfoById(Long id);

    List<RoleInfo> getAllBy();
}
