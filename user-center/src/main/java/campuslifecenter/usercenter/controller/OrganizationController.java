package campuslifecenter.usercenter.controller;

import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.AccountOrganizationKey;
import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.OrganizationService;
import campuslifecenter.usercenter.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static campuslifecenter.usercenter.model.PermissionConst.ORGANIZATION_CHILD;
import static campuslifecenter.usercenter.model.PermissionConst.ORGANIZATION_MEMBER;

@Api("组织管理")
@RestWarpController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/types")
    public List<String> getTypes() {
        return organizationService.getTypes();
    }

    @GetMapping("/{id}")
    public Organization getOrganization(@ApiParam("组织id") @PathVariable("id") int id) {
        return organizationService.get(id);
    }

    @GetMapping("/{id}/member")
    public List<AccountInfo> getMember(@ApiParam("组织id") @PathVariable("id") int id,
                                       @RequestParam(required = false, defaultValue = "") String token) {
        if ("".equals(token)) {
            return organizationService.getMember(id, false);
        }
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        boolean show = permissionService.authentication(accountInfo, id, ORGANIZATION_MEMBER);
        return organizationService.getMember(id, show);
    }

    @GetMapping("/{id}/memberInfo")
    public List<AccountInfo> getMemberInfo(@ApiParam("组织id") @PathVariable("id") int id,
                                           @RequestParam(required = false, defaultValue = "") String token) {
        if ("".equals(token)) {
            return organizationService.getMemberInfo(id, false);
        }
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        boolean show = accountInfo != null && permissionService.authentication(accountInfo, id, ORGANIZATION_MEMBER);
        return organizationService.getMemberInfo(id, show);
    }

    @GetMapping("{id}/memberId")
    public List<String> getMemberId(@ApiParam("组织id") @PathVariable("id") int id){
        return organizationService.getMemberId(id);
    }

    @GetMapping("/public")
    public List<Organization> getPublicOrganization() {
        return organizationService.getPublicOrganization();
    }

    @GetMapping("/{id}/child")
    public List<Organization> getChild(@PathVariable("id") int id) {
        return organizationService.getChild(id);
    }

    @GetMapping("/{id}/parent")
    public Organization getParent(@PathVariable("id") int id) {
        return organizationService.getParent(id);
    }

    @PostMapping("/{id}/addChild")
    public int addChild(@PathVariable("id") int id, @RequestParam String token, @RequestBody Organization organization) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, id, ORGANIZATION_CHILD)) {
            throw new AuthException();
        }
        organization
                .withCreator(accountInfo.getId())
                .withCreateData(new Date())
                .withParent(id)
                .withId(null);
        return organizationService.add(organization);
    }

    @PostMapping("/{id}/invite")
    public boolean invite(@PathVariable("id") int id, @RequestParam String token, @RequestBody List<String> aids) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, id, ORGANIZATION_MEMBER)) {
            throw new AuthException();
        }
        return organizationService.invite(id, aids);
    }

    @PostMapping("/{id}/apply")
    public boolean apply(@PathVariable("id") int id, @RequestParam String aid, @RequestParam String token) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (accountInfo.getOrganizations().stream().map(OrganizationInfo::getId).anyMatch(oid -> oid == id)) {
            return true;
        }
        return organizationService.apply(id, aid);
    }

    @GetMapping("/{id}/applyList")
    public List<AccountInfo> applyList(@PathVariable("id") int id, @RequestParam String token) {
        AccountInfo accountInfo = accountService.getAccountInfo(token);
        if (!permissionService.authentication(accountInfo, id, ORGANIZATION_MEMBER)) {
            throw new AuthException();
        }
        return organizationService.applyList(id)
                .stream()
                .map(AccountOrganizationKey::getAid)
                .map(accountService::getAccount)
                .collect(Collectors.toList());
    }

}
