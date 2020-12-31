package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.Organization;
import campuslifecenter.notice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-center", path = "/organization", contextId = "organization")
public interface OrganizationService {

    @GetMapping("/{id}/member")
    Response<List<AccountInfo>> getMember(@PathVariable("id") int id);

    @GetMapping("{id}/memberId")
    Response<List<String>> getMemberId(@PathVariable("id") int id);

    @GetMapping("/{id}")
    Response<Organization> getOrganization(@PathVariable("id") int id);
}
