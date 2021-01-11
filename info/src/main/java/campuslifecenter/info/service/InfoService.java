package campuslifecenter.info.service;

import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoCollect;

import java.util.List;

public interface InfoService {

    String addInfoCollect(AddInfoRequest addInfoRequest);

    List<String> select(long id, String text);

    InfoCollect getAllAccountInfo(String ref);

    InfoCollect getAccountInfo(String ref, String aid);
}
