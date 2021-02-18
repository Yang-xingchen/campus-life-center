package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Api("权限管理")
@RestWarpController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{aid}/{oid}")
    public List<Permission> getPermission(@PathVariable("aid") String aid, @PathVariable("oid") int oid) {
        return roleService.getRole(aid, oid)
                .stream()
                .flatMap(roleInfo -> roleInfo.getPermissions().stream())
                .collect(Collectors.toList());
    }

}
