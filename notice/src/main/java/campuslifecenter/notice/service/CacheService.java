package campuslifecenter.notice.service;

public interface CacheService {
    String getAccountIdByToken(String token);

    String getAccountNameByID(String id);

    String getOrganizationName(int oid);

    String getCollectName(String ref);
}
