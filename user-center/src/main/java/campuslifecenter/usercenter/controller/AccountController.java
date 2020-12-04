package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.model.SignIn;
import campuslifecenter.usercenter.model.SignInType;
import campuslifecenter.usercenter.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Api("账户管理")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Value("${public-key}")
    private String PUB_KEY;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation("登录信息")
    @GetMapping("/signInInfo")
    public Map<String, String> signInInfo() {
        return Map.of(
                "signInId", accountService.signInId(),
                "pub_key", PUB_KEY
        );
    }

    @ApiOperation("检查登录情况")
    @PostMapping("/checkSignIn")
    public Response<?> checkSignIn(@ApiParam("cookie") String cookie) {
        Response<?> response = new Response<>();
        response.setSuccess(accountService.checkSignInId(cookie));
        return response;
    }

    @ApiOperation("登录")
    @PostMapping("/signIn")
    public Response<?> signIn(@RequestBody SignIn signIn,
                          HttpServletRequest request) {
        if (signIn.getAid() == null || signIn.getCookie() == null) {
            return new Response<>()
                    .setSuccess(false)
                    .setMessage("aid or cookie is null");
        }

        SignInLog sign = new SignInLog();
        sign.setAid(signIn.getAid());
        sign.setCookie(signIn.getCookie());
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
        AccountInfo accountInfo = accountService.getAccountInfo(sign.getCookie());
        if (accountInfo == null) {
            return new Response<>()
                    .setCode(SignInType.ACCOUNT_NOT_EXIST.code)
                    .setSuccess(false)
                    .setMessage(SignInType.ACCOUNT_NOT_EXIST.message);
        }
        return ((Response<AccountInfo>)res).setData(accountInfo);
    }

    @ApiOperation("登出")
    @PostMapping("/signOut")
    public boolean signOut(@ApiParam("账户登录id") String aid) {
        accountService.signOut(aid);
        return true;
    }

}
