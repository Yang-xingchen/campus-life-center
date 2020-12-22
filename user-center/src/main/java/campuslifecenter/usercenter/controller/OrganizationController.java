package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    public Response<List<AccountInfo>> getMember(int id) {
        return Response.withData(organizationService.getMember(id));
    }

}
