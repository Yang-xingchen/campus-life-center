package campuslifecenter.usercenter.service;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.UserInfo;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean signUp(User user);

    boolean signIn(User user);

    UserInfo getUser(Long id);

    List<UserInfo> getUsers(List<Long> ids);

    Set<Role> getRole(Long id);

    List<UserInfo> getUserList();
}
