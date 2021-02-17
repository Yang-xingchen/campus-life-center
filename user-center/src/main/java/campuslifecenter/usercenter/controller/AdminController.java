package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.model.PermissionConst;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api("管理员")
@RestWarpController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;

    private boolean checkToken(String token) {
        return Optional
                .ofNullable(accountService.getAccountInfo(token))
                .map(AccountInfo::getOrganizations)
                .orElseGet(ArrayList::new)
                .stream()
                .map(OrganizationInfo::getId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id == 0);
    }

    @PostMapping("/addAccount")
    @ApiOperation("添加用户")
    public Map<Boolean, List<Account>> addAccount(@ApiParam("添加信息") @RequestBody List<Account> accounts, @RequestParam String token) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, 1, PermissionConst.SYSTEM_ACCOUNT)) {
            throw new AuthException();
        }
        return accountService.addAllAccount(accounts);
    }

    @GetMapping("/account")
    @ApiOperation("查询全部用户")
    public List<AccountInfo> account(@ApiParam("token") @RequestBody String token) {
        if (!checkToken(token)) {
            throw new AuthException();
        }
        return accountService.findAllAccount();
    }

}
