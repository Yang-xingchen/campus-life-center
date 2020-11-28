package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.model.SignIn;
import campuslifecenter.usercenter.model.SignInType;
import campuslifecenter.usercenter.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/signInId")
    public String signInId() {
        return accountService.signInId();
    }

    @PostMapping("/checkSignIn")
    public Response<?> checkSignIn(String cookie) {
        Response<?> response = new Response<>();
        response.setSuccess(accountService.checkSignInId(cookie));
        return response;
    }

    @PostMapping("/signIn")
    public Response<?> signIn(@RequestBody SignIn signIn,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          @CookieValue(value = "sso_uuid", required = false) String ssoUuid) {
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
        if (!signInType.success) {
            return new Response<>()
                    .setSuccess(false)
                    .setMessage(signInType.name());
        }
        if (ssoUuid==null) {
            response.addCookie(new Cookie("sso_uuid", sign.getCookie()));
        }
        AccountInfo accountInfo = accountService.getAccountInfo(sign.getCookie());
        if (accountInfo == null) {
            return new Response<>()
                    .setSuccess(false)
                    .setMessage("account not find");
        }
        return new Response<>()
                .setSuccess(true)
                .setData(accountInfo);
    }

    @PostMapping("/signOut")
    public boolean signOut(String aid,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        accountService.signOut(aid);
        Arrays.stream(request.getCookies())
                .filter(cookie -> Objects.equals(cookie.getName(), "sso_uuid"))
                .forEach(cookie -> {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                });
        return true;
    }

}
