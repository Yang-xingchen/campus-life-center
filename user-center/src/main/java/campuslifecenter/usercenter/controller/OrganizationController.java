package campuslifecenter.usercenter.controller;

import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.Response;
import campuslifecenter.usercenter.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{id}/member")
    public Response<List<AccountInfo>> getMember(@PathVariable("id") int id) {
        return Response.withData(organizationService.getMember(id));
    }

    @GetMapping("{id}/memberId")
    Response<List<String>> getMemberId(@PathVariable("id") int id){
        return Response.withData(organizationService.getMemberId(id));
    }
}
