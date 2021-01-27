package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
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
@RestController
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
                .map(AccountInfo.AccountOrganizationInfo::getOid)
                .filter(Objects::nonNull)
                .anyMatch(id -> id == 0);
    }

    @PostMapping("/addAccount")
    @ApiOperation("添加用户")
    public Response<Map<Boolean, List<Account>>> addAccount(@ApiParam("添加信息") @RequestBody AddAccountRequest request) {
        return Response.withData(() -> {
            if (!checkToken(request.getToken())) {
                throw new ResponseException("没有权限", 403);
            }
            return accountService.addAllAccount(request.getAccounts());
        });
    }

    @GetMapping("/account")
    @ApiOperation("查询全部用户")
    public Response<List<AccountInfo>> account(@ApiParam("token") @RequestBody String token) {
        return Response.withData(() -> {
            if (!checkToken(token)) {
                throw new ResponseException("没有权限", 403);
            }
            return accountService.findAllAccount();
        });
    }

}
