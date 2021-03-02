package campuslifecenter.search.service.impl;

import campuslifecenter.common.model.LazyList;
import campuslifecenter.search.model.NoticeSearch;
import campuslifecenter.search.repository.NoticeRepository;
import campuslifecenter.search.service.SearchService;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public LazyList<NoticeSearch> searchNotice(String keyword, int page) {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
        queryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        queryBuilder.tieBreaker(0.3f);
        queryBuilder.minimumShouldMatch("80%");
        Page<NoticeSearch> search = noticeRepository.search(queryBuilder, PageRequest.of(page, 20));
        LazyList<NoticeSearch> lazyList = new LazyList<>();
        lazyList.setPage(page);
        lazyList.setTotal(search.getTotalElements());
        lazyList.setSize(search.getSize());
        lazyList.setItems(search.getContent());
        return lazyList;
    }

}
