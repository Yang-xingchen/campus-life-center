package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;

import java.util.List;

public interface UserService {

    boolean singUp(User user);

    boolean singIn(User user);

    List<Role> getRole(Long id);

}
