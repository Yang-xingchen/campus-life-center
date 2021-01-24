package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.model.InfoSourceCollect;

import java.util.List;

public interface AccountInfoService {

    InfoSourceCollect getSubmit(String ref, String aid);

    List<AccountSaveInfo> getAccountSaveInfo(List<Long> ids, String aid);

    InfoSourceCollect getAllAccountSubmit(String ref);

    List<String> select(long id, String text);

    Boolean submit(List<AccountSubmit> infos);

}
