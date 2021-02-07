package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.model.RoleInfo;
import org.springframework.cloud.sleuth.annotation.NewSpan;

import java.util.List;

public interface OrganizationService {

    Organization get(int id);

    List<OrganizationInfo> getOrganization(String aid);

    List<RoleInfo> getRole(String aid, int oid);

    List<AccountInfo> getMember(int id);

    List<String> getMemberId(int id);

    List<Organization> getPublicOrganization();
}
