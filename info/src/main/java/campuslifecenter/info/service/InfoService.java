package campuslifecenter.info.service;

import campuslifecenter.info.model.InfoCollectRequest;
import campuslifecenter.info.model.InfoItem;

import java.util.List;
import java.util.function.Consumer;

public interface InfoService {

    String addCollect(InfoCollectRequest.AddInfoRequest addInfoRequest);

    InfoItem getInfoItem(long id, Consumer<InfoItem> consumer);

    boolean updateCollectAccount(long id, List<String> aids);

    List<InfoItem> getExistInfo();

    long getRoot(String ref);
}
