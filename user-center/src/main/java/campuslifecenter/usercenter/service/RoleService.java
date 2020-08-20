package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Role findRole(Long id);

    Set<User> getUserList(Long id);

    boolean createRole(String name, Long creator);

    boolean addToRole(Role role, User user);

    boolean addToRole(Role role, Set<User> users);

    boolean addToRole(Long roleId, Set<Long> users);

    List<Role> getRolelist();
}
