package campuslifecenter.notice.service;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;

public interface CacheService {
    String getAccountIdByToken(String token);

    String getAccountNameByID(String id);

    String getOrganizationName(int oid);

    @NewSpan("get collect name")
    String getCollectName(@SpanTag("ref") String ref);
}
