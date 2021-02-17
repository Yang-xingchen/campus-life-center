package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.entry.OrganizationSaveInfo;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;

import java.util.List;

public interface AccountInfoService {

    InfoItem.CompositeItem getSubmit(String aid, long rootId);

    List<AccountSaveInfo> getSaveByAccount(List<Long> ids, String aid);

    InfoSourceCollect getSubmitByRoot(long rootId);

    List<String> select(long id, int type, String text);

    Boolean submit(List<AccountSubmit> infos);

    List<OrganizationSaveInfo> getSaveByOrganization(List<Long> ids, int id);
}
