package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountInfo;
import campuslifecenter.info.entry.InfoAccountList;
import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoCollect;

import java.util.List;

public interface InfoService {

    String addInfoCollect(AddInfoRequest addInfoRequest);

    List<String> select(long id, String text);

    InfoCollect getAllAccountSubmit(String ref);

    InfoCollect getCollectList(String ref, String aid);

    List<AccountInfo> getAccountSaveInfo(List<Long> ids, String aid);

    Boolean submit(List<InfoAccountList> infos);
}
