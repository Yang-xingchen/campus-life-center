package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.PermissionService;
import campuslifecenter.usercenter.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static campuslifecenter.usercenter.model.PermissionConst.ORGANIZATION_MEMBER;

@Api("角色管理")
@RestWarpController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/{oid}/add")
    public int addRole(@PathVariable("oid") int oid, @RequestParam String token, @RequestBody AddRoleRequest role) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, oid, ORGANIZATION_MEMBER)) {
            throw new AuthException("Have no legal power");
        }
        role.withOid(oid).withAid(null);
        return roleService.add(role);
    }

    @GetMapping("/{oid}/{rid}/remove")
    public boolean remove(@PathVariable("oid") int oid,
                          @PathVariable("rid") int rid,
                          @RequestParam String token,
                          @RequestParam String aid) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, oid, ORGANIZATION_MEMBER)) {
            throw new AuthException("Have no legal power");
        }
        return roleService.remove(oid, rid, aid);
    }

}
