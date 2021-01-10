package campuslifecenter.notice.service;

import java.util.List;
import java.util.Set;

public interface TagService {

    Set<String> tagList();

    void addTag(List<String> tags, long nid);

}
