package campuslifecenter.usercenter.service;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.RoleInfo;

import java.util.List;
import java.util.Set;

public interface RoleService {

    RoleInfo findRole(Long id);

    Set<User> getUserList(Long id);

    boolean createRole(String name, Long creator);

    boolean addToRole(Role role, Set<User> users);

    List<RoleInfo> getRoleList();
}
