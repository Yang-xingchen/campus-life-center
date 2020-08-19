package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;
import campuslifecenter.usercenter.repository.UserRepository;
import campuslifecenter.usercenter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceimpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean singUp(User user) {
        Objects.nonNull(user.getSingInId());
        Objects.nonNull(user.getName());
        Objects.nonNull(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return false;
    }

    @Override
    public boolean singIn(User user) {
        String psd = userRepository.findPasswordById(user.getId());
        Objects.requireNonNull(psd, "user not find");
        return passwordEncoder.matches(user.getPassword(), psd);
    }

    @Override
    public List<Role> getRole(Long id) {
        return userRepository.findById(id).map(User::getRoles).orElse(List.of());
    }

}
