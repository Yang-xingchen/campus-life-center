package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.model.RoleInfo;

import java.util.List;

public interface OrganizationService {

    Organization get(int id);

    List<OrganizationInfo> getOrganization(String aid);

    List<RoleInfo> getRole(String aid, int oid);

    List<AccountInfo> getMember(int id, boolean showHide);

    List<AccountInfo> getMemberInfo(int id, boolean showHide);

    List<String> getMemberId(int id);

    List<Organization> getPublicOrganization();

    List<Organization> getChild(int id);

    Organization getParent(int id);

    int add(Organization organization);

    boolean invite(int id, List<String> aids);

    boolean apply(int id, String signId);

    boolean addAccount(List<String> aids);

    List<AccountOrganization> applyList(int id);

    List<String> getTypes();
}
