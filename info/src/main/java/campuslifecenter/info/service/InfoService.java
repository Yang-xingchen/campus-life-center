package campuslifecenter.info.service;

import campuslifecenter.info.model.AddInfoRequest;

import java.util.List;

public interface InfoService {

    String addInfoCollect(AddInfoRequest addInfoRequest);

    List<String> select(long id, String text);
}
