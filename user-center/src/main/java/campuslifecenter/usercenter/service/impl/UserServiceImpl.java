package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.model.Gender;
import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.usercenter.repository.UserRepository;
import campuslifecenter.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public boolean singUp(User user) {
        Objects.nonNull(user.getSingInId());
        Objects.nonNull(user.getName());
        Objects.nonNull(user.getPassword());
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setGender(user.getGender() == null ? Gender.UNKNOWN : user.getGender());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean singIn(User user) {
        String psd = userRepository
                .findBySingInId(user.getSingInId())
                .map(User::getPassword)
                .orElse(null);
        return PASSWORD_ENCODER.matches(user.getPassword(), psd);
    }

    @Override
    public User getUserAllInfo(Long id) {
        User user = userRepository.getOne(id);
        user.getRoles();
        user.getCreateRole();
        return user;
    }


    @Override
    public Set<Role> getRole(Long id) {
        return userRepository
                .findById(id)
                .map(User::getRoles)
                .orElse(Set.of());
    }

    @Override
    public List<User> getUserList() {
        return userRepository
                .findAll();
    }

}
