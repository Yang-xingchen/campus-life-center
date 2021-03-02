package campuslifecenter.search.service;

import campuslifecenter.common.model.LazyList;
import campuslifecenter.search.model.NoticeSearch;

public interface SearchService {

    LazyList<NoticeSearch> searchNotice(String keyword, int page);

}
