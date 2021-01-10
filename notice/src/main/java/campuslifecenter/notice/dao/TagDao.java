package campuslifecenter.notice.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagDao {

    @Select({
            "SELECT distinct(tag) FROM notice_tag"
    })
    List<String> tagList();
}
