package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean singUp(User user);

    boolean singIn(User user);

    User getUserAllInfo(Long id);

    Set<Role> getRole(Long id);

    List<User> getUserList();
}
