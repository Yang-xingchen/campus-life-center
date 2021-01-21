package campuslifecenter.info.dao;

import campuslifecenter.info.service.impl.InfoServiceImpl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InfoDao {

    @Insert({
            "insert into info_account_list (source, id, ",
            "aid, index, text)",
            "values (#{source,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
            "#{aid,jdbcType=VARCHAR}, #{index,jdbcType=INT}, #{text,jdbcType=VARCHAR})",
            "ON DUPLICATE KEY",
            "UPDATE `text`=#{newText,jdbcType=VARCHAR}"
    })
    int insertOrUpdate(InfoServiceImpl.UpdateInfoAccountList infoAccountList);

    @Select({
            "SELECT id FROM info"
    })
    List<Long> infosId();

}
