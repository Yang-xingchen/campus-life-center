package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.model.AccountInfo;

import java.util.List;

public interface OrganizationService {

    /**
     * 角色
     * @param aid 账户id
     * @param oid 组织id
     * @return 角色名, null为未加入
     */
    List<AccountOrganization> role(String aid, int oid);

    List<AccountInfo> getMember(int id);

    List<String> getMemberId(int id);
}
