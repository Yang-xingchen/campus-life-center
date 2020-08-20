package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.model.Role;
import campuslifecenter.usercenter.model.User;
import campuslifecenter.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class RoleController {

    @Autowired
    public final RoleService roleService;

    @GetMapping("/role/roleList")
    public List<Role> roleList() {
        return roleService.getRolelist();
    }

    @GetMapping("/role/role")
    public Role role(Long id) {
        return roleService.findRole(id);
    }

    @GetMapping("/role/userList")
    public Set<User> userList(Long id) {
        return roleService.getUserList(id);
    }

    @PostMapping("/role/createRole")
    public boolean createRole(Role role) {
        return roleService.createRole(role.getName(), role.getBelong().getId());
    }

    @PostMapping("/role/add")
    public boolean add(Role role) {
        return roleService.addToRole(role, role.getUsers());
    }
}