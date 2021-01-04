package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.*;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/account")
@Api("账户管理")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private EncryptionService encryptionService;

    @ApiOperation("登录信息")
    @GetMapping("/signInInfo")
    public Map<String, String> signInInfo() {
        return Map.of(
                "signInId", accountService.signInId(),
                "pub_key", encryptionService.getPublecKey()
        );
    }

    @ApiOperation("检查登录情况")
    @PostMapping("/checkToken")
    public boolean checkSignIn(@ApiParam("token") @RequestBody String cookie) {
        return accountService.checkToken(cookie);
    }

    @ApiOperation("获取信息")
    @GetMapping("/info/{token}")
    public Response<AccountInfo> info(@ApiParam("token") @PathVariable String token) {
        return Response.withData(()->accountService.getAccountInfo(token), throwable -> "token " + token + " not find");
    }

    @ApiOperation("获取信息")
    @GetMapping("/{id}/info")
    public Response<AccountInfo> infoById(@ApiParam("id") @PathVariable String id) {
        return Response.withData(()->accountService.getAccount(id), throwable -> "id: " + id + " not find");
    }

    @ApiOperation("获取信息")
    @PostMapping("/infos")
    public Response<List<AccountInfo>> infoByIds(@ApiParam("id列表") List<String> ids) {
        return Response.withData(() -> accountService.getAccountInfos(ids));
    }

    @ApiOperation("登录")
    @PostMapping("/signIn")
    public Response<?> signIn(@ApiParam("登录信息") @RequestBody SignIn signIn,
                          HttpServletRequest request) {
        if (signIn.getAid() == null || signIn.getSignInId() == null) {
            return new Response<>()
                    .setSuccess(false)
                    .setMessage("aid or sign in id is null");
        }

        SignInLog sign = new SignInLog();
        sign.setAid(signIn.getAid());
        sign.setToken(signIn.getSignInId());
        sign.setIp(request.getRemoteAddr());
        sign.setSignInTime(new Date());
        SignInType signInType = accountService.signIn(signIn.getAid(), signIn.getPassword(), sign);
        Response<?> res = new Response<>()
                .setCode(signInType.code)
                .setSuccess(signInType.success)
                .setMessage(signInType.message);
        if (!signInType.success) {
            return res;
        }
        AccountInfo accountInfo = accountService.getAccountInfo(sign.getToken());
        if (accountInfo == null) {
            return new Response<>()
                    .setCode(SignInType.ACCOUNT_NOT_EXIST.code)
                    .setSuccess(false)
                    .setMessage(SignInType.ACCOUNT_NOT_EXIST.message);
        }
        return ((Response<AccountInfo>)res).setData(accountInfo);
    }

    @ApiOperation("登出")
    @GetMapping("{id}/signOut")
    public boolean signOut(@ApiParam("账户登录id") @PathVariable("id") String id) {
        accountService.signOut(id);
        return true;
    }

    @ApiOperation("进入安全模式")
    @PostMapping("/{aid}/startSecurity")
    public boolean startSecurity(@ApiParam("账户id") @PathVariable("aid") String aid,
                                 @ApiParam("安全信息") @RequestBody SecurityRequest securityRequest) {
        if (!Objects.equals(aid, securityRequest.getAid())) {
            return false;
        }
        return encryptionService.startSecurity(aid, securityRequest.getPwd(), securityRequest.getKey());
    }

    @ApiOperation("退出安全模式")
    @PostMapping("/{aid}/exitSecurity")
    public boolean exitSecurity(@ApiParam("账户id") @PathVariable("aid") String aid) {
        return encryptionService.exitSecurity(aid);
    }
}
