package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.AccountRequest;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                .map(Organization::getId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id == 0);
    }

    @PostMapping("/addAccount")
    @ApiOperation("添加用户")
    public Response<Map<Boolean, List<Account>>> addAccount(@ApiParam("添加信息") @RequestBody AccountRequest request) {
        if (!checkToken(request.getToken())) {
            return new Response<Map<Boolean, List<Account>>>().setCode(403).setSuccess(false).setMessage("没有权限");
        }
        return Response.withData(accountService.addAllAccount(request.getAccounts()));
    }

    @GetMapping("/account")
    @ApiOperation("查询全部用户")
    public Response<List<AccountInfo>> account(@ApiParam("token") @RequestBody String token) {
        if (!checkToken(token)) {
            return new Response<List<AccountInfo>>().setCode(403).setSuccess(false).setMessage("没有权限");
        }
        return Response.withData(accountService.findAllAccount());
    }

}
