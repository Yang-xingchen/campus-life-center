package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.Role;
import campuslifecenter.common.model.User;
import campuslifecenter.common.model.projections.RoleInfo;
import campuslifecenter.usercenter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    @Autowired
    public final RoleService roleService;

    @GetMapping("/roleList")
    public List<RoleInfo> roleList() {
        return roleService.getRoleList();
    }

    @GetMapping("/roleInfo/{id}")
    public RoleInfo roleInfo(@PathVariable("id") Long id) {
        return roleService.findRole(id);
    }

    @GetMapping("/userList/{id}")
    public Set<User> userList(@PathVariable("id") Long id) {
        return roleService.getUserList(id);
    }

    @PostMapping("/createRole")
    public boolean createRole(@RequestBody Role role) {
        return roleService.createRole(role.getName(), role.getBelong().getId());
    }

    @PostMapping("/addUsers")
    public boolean addUsers(@RequestBody Role role) {
        return roleService.addToRole(role, role.getUsers());
    }
}
