package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("组织管理")
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{id}")
    public Response<Organization> getOrganization(@ApiParam("组织id") @PathVariable("id") int id) {
        return Response.withData(organizationService.get(id));
    }

    @GetMapping("/{id}/member")
    public Response<List<AccountInfo>> getMember(@ApiParam("组织id") @PathVariable("id") int id) {
        return Response.withData(organizationService.getMember(id));
    }

    @GetMapping("{id}/memberId")
    Response<List<String>> getMemberId(@ApiParam("组织id") @PathVariable("id") int id){
        return Response.withData(organizationService.getMemberId(id));
    }
}
