package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api("组织管理")
@RestWarpController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{id}")
    public Organization getOrganization(@ApiParam("组织id") @PathVariable("id") int id) {
        return organizationService.get(id);
    }

    @GetMapping("/{id}/member")
    public List<AccountInfo> getMember(@ApiParam("组织id") @PathVariable("id") int id) {
        return organizationService.getMember(id);
    }

    @GetMapping("{id}/memberId")
    public List<String> getMemberId(@ApiParam("组织id") @PathVariable("id") int id){
        return organizationService.getMemberId(id);
    }

    @GetMapping("/public")
    public List<Organization> getPublicOrganization() {
        return organizationService.getPublicOrganization();
    }

}
