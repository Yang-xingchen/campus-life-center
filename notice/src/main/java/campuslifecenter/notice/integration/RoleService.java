package campuslifecenter.notice.integration;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@FeignClient(name = "user-center", path = "/role", contextId = "role")
public interface RoleService {


    @GetMapping("/roleList")
    List<Role> roleList();

    @GetMapping("/role")
    Role role(Long id);

    @GetMapping("/userList")
    Set<User> userList(Long id);

}
