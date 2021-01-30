package campuslifecenter.info.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InfoDao {

    @Select("SELECT id FROM info WHERE `hide`=0")
    List<Long> infosId();

}
