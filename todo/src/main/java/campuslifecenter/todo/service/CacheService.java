package campuslifecenter.todo.service;

public interface CacheService {
    String getAccountIdByToken(String token);

    String getAccountNameByID(String id);

}
