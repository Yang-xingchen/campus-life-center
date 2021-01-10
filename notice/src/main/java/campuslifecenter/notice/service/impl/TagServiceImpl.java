package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.dao.TagDao;
import campuslifecenter.notice.entry.NoticeTagKey;
import campuslifecenter.notice.mapper.NoticeTagMapper;
import campuslifecenter.notice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;
    @Autowired
    private NoticeTagMapper tagMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.cache.tags}")
    private String TAGS_CACHE;

    @Override
    public Set<String> tagList() {
        BoundSetOperations<String, String> tagOps = redisTemplate.boundSetOps(TAGS_CACHE);
        if (tagOps.size() != 0) {
            return tagOps.members();
        }
        return tagDao
                .tagList()
                .stream()
                .peek(tagOps::add)
                .collect(Collectors.toSet());
    }

    @Override
    public void addTag(List<String> tags, long nid) {
        BoundSetOperations<String, String> tagOps = redisTemplate.boundSetOps(TAGS_CACHE);
        tags.stream()
                .peek(tagOps::add)
                .map(tag -> new NoticeTagKey().withNid(nid).withTag(tag))
                .forEach(tagMapper::insert);
    }

}