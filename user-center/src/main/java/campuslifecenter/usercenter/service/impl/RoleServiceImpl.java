package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.model.User;
import campuslifecenter.usercenter.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Override
    public List<User> getUserList(Long id) {
        return null;
    }

}
