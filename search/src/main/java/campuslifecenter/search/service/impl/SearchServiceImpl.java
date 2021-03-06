package campuslifecenter.search.service.impl;

import campuslifecenter.common.model.LazyList;
import campuslifecenter.search.model.NoticeSearch;
import campuslifecenter.search.repository.NoticeRepository;
import campuslifecenter.search.service.SearchService;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Value("${search.page-size}")
    private int pageSize;

    @Override
    @NewSpan("search notice")
    public LazyList<NoticeSearch> searchNotice(@SpanTag("key word") String keyword, @SpanTag("page") int page) {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, "tag^5", "title^3", "content");
        queryBuilder.type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
        queryBuilder.tieBreaker(0.3f);
        queryBuilder.minimumShouldMatch("70%");
        Page<NoticeSearch> search = noticeRepository.search(queryBuilder, PageRequest.of(page, pageSize));
        LazyList<NoticeSearch> lazyList = new LazyList<>();
        lazyList.setPage(page);
        lazyList.setTotal(search.getTotalElements());
        lazyList.setSize(search.getSize());
        lazyList.setItems(search.getContent());
        return lazyList;
    }

}
