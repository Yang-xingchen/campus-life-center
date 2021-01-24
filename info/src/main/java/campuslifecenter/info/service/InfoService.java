package campuslifecenter.info.service;

import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoItem;

import java.util.List;
import java.util.function.Consumer;

public interface InfoService {

    String addInfoCollect(AddInfoRequest addInfoRequest);

    InfoItem getCollectItem(long id, Consumer<InfoItem> consumer);

    List<InfoItem> getExistInfo();
}
