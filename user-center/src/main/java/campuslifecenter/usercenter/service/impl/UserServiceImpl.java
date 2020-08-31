package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.model.Gender;
import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.SignInUser;
import campuslifecenter.common.model.projections.UserInfo;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public boolean signUp(User user) {
        Objects.nonNull(user.getId());
        Objects.nonNull(user.getName());
        Objects.nonNull(user.getPassword());
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setGender(user.getGender() == null ? Gender.UNKNOWN : user.getGender());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean signIn(User user) {
        String psd = userRepository
                .getSingInBySignId(user.getId())
                .map(SignInUser::getPassword)
                .orElse(null);
        return PASSWORD_ENCODER.matches(user.getPassword(), psd);
    }

    @Override
    public UserInfo getUser(Long id) {
        return userRepository.getInfoById(id).orElse(null);
    }

    @Override
    public List<UserInfo> getUsers(List<Long> ids) {
        return ids
                .stream()
                .map(userRepository::getInfoById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


    @Override
    public Set<Role> getRole(Long id) {
        return userRepository
                .findById(id)
                .map(User::getRoles)
                .orElse(Set.of());
    }

    @Override
    public List<UserInfo> getUserList() {
        return userRepository.getAllBy();
    }

}
