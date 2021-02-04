package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.AddAccountRequest;
import campuslifecenter.usercenter.service.AccountService;
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

    private boolean checkToken(String token) {
        return Optional
                .ofNullable(accountService.getAccountInfo(token))
                .map(AccountInfo::getOrganizations)
                .orElseGet(ArrayList::new)
                .stream()
                .map(AccountInfo.OrganizationInfo::getId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id == 0);
    }

    @PostMapping("/addAccount")
    @ApiOperation("添加用户")
    public Map<Boolean, List<Account>> addAccount(@ApiParam("添加信息") @RequestBody AddAccountRequest request) {
        if (!checkToken(request.getToken())) {
            throw new AuthException();
        }
        return accountService.addAllAccount(request.getAccounts());
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
