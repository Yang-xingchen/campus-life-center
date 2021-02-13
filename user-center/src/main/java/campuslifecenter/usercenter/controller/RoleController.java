package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.model.UpdateRoleRequest;
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
        if (role.getAids().isEmpty()) {
            throw new ResponseException("no account");
        }
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, oid, ORGANIZATION_MEMBER)) {
            throw new AuthException();
        }
        role.withOid(oid).withAid(null);
        return roleService.add(role);
    }

    @PostMapping("/{oid}/{rid}/update")
    public boolean update(@PathVariable("oid")int oid,
                          @PathVariable int rid,
                          @RequestParam String token,
                          @RequestBody UpdateRoleRequest role) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, oid, ORGANIZATION_MEMBER)) {
            throw new AuthException();
        }
        role.setId(rid).setOid(oid);
        return roleService.update(role);
    }

}
