package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.*;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import campuslifecenter.usercenter.service.OrganizationService;
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
import java.util.stream.Collectors;

@Api("账户管理")
@RestWarpController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation("登录信息")
    @GetMapping("/signInInfo")
    public Map<String, String> signInInfo() {
        return Map.of(
                "signInId", accountService.signInId(),
                "pubKey", encryptionService.getPublecKey()
        );
    }

    @ApiOperation("检查登录情况")
    @PostMapping("/checkToken")
    public boolean checkSignIn(@ApiParam("token") @RequestBody String cookie) {
        return accountService.checkToken(cookie);
    }

    @ApiOperation("获取信息")
    @GetMapping("/info/{token}")
    public AccountInfo info(@ApiParam("token") @PathVariable String token) {
        return accountService.getAccountInfo(token);
    }

    @ApiOperation("获取信息")
    @GetMapping("/{id}/info")
    public AccountInfo infoById(@ApiParam("id") @PathVariable String id) {
        return accountService.getAccount(id);
    }

    @ApiOperation("获取信息")
    @PostMapping("/infos")
    public List<AccountInfo> infoByIds(@ApiParam("id列表") List<String> ids) {
        return accountService.getAccountInfos(ids);
    }

    @ApiOperation("登录")
    @PostMapping("/signIn")
    public AccountInfo signIn(@ApiParam("登录信息") @RequestBody SignInRequest signIn,
                          HttpServletRequest request) {
        if (signIn.getAid() == null || signIn.getSignInId() == null) {
            throw new ResponseException("未知登录id", 501);
        }
        SignInLog sign = new SignInLog();
        sign.setAid(signIn.getAid());
        sign.setToken(signIn.getSignInId());
        sign.setIp(request.getRemoteAddr());
        sign.setSignInTime(new Date());
        if (!accountService.signIn(signIn.getAid(), signIn.getPassword(), sign)) {
            throw new ResponseException("未知错误");
        }
        return accountService.getAccountInfo(sign.getToken());
    }

    @GetMapping("/signIn")
    public AccountInfo signIn(@RequestParam String signInId, @RequestParam String token,
                              HttpServletRequest request){
        SignInLog sign = new SignInLog();
        sign.setToken(signInId);
        sign.setIp(request.getRemoteAddr());
        if (!accountService.signInByToken(token, sign)) {
            throw new ResponseException("未知错误");
        }
        return accountService.getAccountInfo(sign.getToken());
    }

    @PostMapping("{id}/update")
    public boolean update(@RequestParam String token, @PathVariable("id") String id, @RequestBody UpdateAccount account) {
        String aid = accountService.getAccountInfo(token).getId();
        AuthException.checkThrow(id, aid);
        account.setId(aid);
        if (accountService.update(account)) {
            accountService.signOut(account.getId(), null);
            return true;
        }
        return false;
    }

    @ApiOperation("登出")
    @GetMapping("{id}/signOut")
    public boolean signOut(@ApiParam("账户登录id") @PathVariable("id") String id,
                           @RequestParam String token) {
        String aid = accountService.getAccountInfo(token).getId();
        AuthException.checkThrow(aid, id);
        return accountService.signOut(id, token);
    }

    @ApiOperation("登出")
    @GetMapping("{id}/signOutAll")
    public boolean signOutAll(@ApiParam("账户登录id") @PathVariable("id") String id, @RequestParam String token) {
        String aid = accountService.getAccountInfo(token).getId();
        AuthException.checkThrow(aid, id);
        return accountService.signOut(id, null);
    }

    @ApiOperation("登出")
    @GetMapping("{id}/signOutOther")
    public boolean signOutOther(@ApiParam("账户登录id") @PathVariable("id") String id, @RequestParam String token) {
        String aid = accountService.getAccountInfo(token).getId();
        AuthException.checkThrow(aid, id);
        return accountService.signOutOther(id, token);
    }

    @ApiOperation("登录日记")
    @GetMapping("{id}/signInLog")
    private List<SignInLog> signInLogs(@PathVariable("id") String id, @RequestParam String token) {
        String aid = accountService.getAccountInfo(token).getId();
        AuthException.checkThrow(aid, id);
        return accountService.signInLogs(id);
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


    @ApiOperation("获取管理成员")
    @GetMapping("/adminMember")
    public List<AccountInfo> getAdminMember(@RequestParam String token) {
        return accountService
                .getAccountInfo(token)
                .getOrganizations()
                .stream()
                .filter(organizationInfo -> organizationInfo
                        .getRoles()
                        .stream()
                        .anyMatch(roleInfo -> roleInfo
                                .getPermissions()
                                .stream()
                                .map(Permission::getName)
                                .anyMatch(PermissionConst.ORGANIZATION_MEMBER::equals)
                        )
                )
                .map(OrganizationInfo::getId)
                .flatMap(oid -> organizationService.getMember(oid, true).stream())
                .distinct()
                .collect(Collectors.toList());
    }

}
