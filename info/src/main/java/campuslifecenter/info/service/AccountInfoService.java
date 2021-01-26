package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;

import java.util.List;

public interface AccountInfoService {

    InfoItem.CompositeItem getSubmit(String ref, String aid, long rootId);

    List<AccountSaveInfo> getSaveByAccount(List<Long> ids, String aid);

    InfoSourceCollect getSubmitByRef(String ref, long rootId);

    List<String> select(long id, String text);

    Boolean submit(List<AccountSubmit> infos);

}
