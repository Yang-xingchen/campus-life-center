package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.usercenter.repository.RoleRepository;
import campuslifecenter.usercenter.repository.UserRepository;
import campuslifecenter.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;


    @Override
    public Role findRole(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Set<User> getUserList(Long id) {
        return roleRepository.findUsersById(id);
    }

    @Override
    public boolean createRole(String name, Long creator) {
        Objects.requireNonNull(name);
        Role role = new Role();
        role.setName(name);
        if (null != creator) {
            role.setBelong(userRepository.getOne(creator));
        }
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean addToRole(Role role, User user) {
        role = roleRepository.getOne(role.getId());
        user = userRepository.getOne(user.getId());
        role.getUsers().add(user);
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean addToRole(Role role, Set<User> users) {
        role = roleRepository.getOne(role.getId());
        List<User> userList = userRepository.findAllById(users
                .stream()
                .map(User::getId)
                .collect(Collectors.toList()));
        users = new HashSet<>();
        users.addAll(userList);
        role.getUsers().addAll(users);
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean addToRole(Long roleId, Set<Long> users) {
        Role role = roleRepository.getOne(roleId);
        List<User> userList = userRepository.findAllById(users);
        role.getUsers().addAll(userList);
        roleRepository.save(role);
        return true;
    }

    @Override
    public List<Role> getRolelist() {
        return roleRepository.findAll();
    }

}
