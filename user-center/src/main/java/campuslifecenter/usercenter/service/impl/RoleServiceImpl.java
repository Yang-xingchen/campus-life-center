package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.RoleInfo;
import campuslifecenter.usercenter.repository.RoleRepository;
import campuslifecenter.usercenter.repository.UserRepository;
import campuslifecenter.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;


    @Override
    public RoleInfo findRole(Long id) {
        return roleRepository.getInfoById(id);
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
    public boolean addToRole(Role role, Set<User> users) {
        Objects.requireNonNull(role);
        Objects.requireNonNull(role.getId());
        Objects.requireNonNull(users);
        Role finalRole = roleRepository.findById(role.getId()).orElseThrow();
        List<User> userList = userRepository.findAllById(users
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList()))
                .stream()
                .peek(user -> user.getRoles().add(finalRole))
                .collect(Collectors.toList());
        userRepository.saveAll(userList);
        return true;
    }

    @Override
    public List<RoleInfo> getRoleList() {
        return roleRepository.getAllBy();
    }

}
