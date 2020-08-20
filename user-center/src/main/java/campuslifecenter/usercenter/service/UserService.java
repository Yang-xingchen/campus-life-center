package campuslifecenter.usercenter.service;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean singUp(User user);

    boolean singIn(User user);

    User getUserAllInfo(Long id);

    Set<Role> getRole(Long id);

    List<User> getUserList();
}
