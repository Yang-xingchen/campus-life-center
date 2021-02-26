package campuslifecenter.search.repository;

import campuslifecenter.search.model.Notice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NoticeRepository extends ElasticsearchRepository<Notice, Integer> {
}
