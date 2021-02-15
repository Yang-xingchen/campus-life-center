package campuslifecenter.notice.service;

import java.util.List;

public interface OrganizationSubscribeService {

    List<String> getSubscribeAccountId(int id);

    boolean subscribe(String aid, int oid);

    boolean cancelSubscribe(String aid, int oid);
}
