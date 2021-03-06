package campuslifecenter.search.repository;

import campuslifecenter.search.model.NoticeSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NoticeRepository extends ElasticsearchRepository<NoticeSearch, Long> {
}
