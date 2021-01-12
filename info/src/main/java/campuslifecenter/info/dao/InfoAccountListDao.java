package campuslifecenter.info.dao;

import campuslifecenter.info.service.impl.InfoServiceImpl;
import org.apache.ibatis.annotations.Insert;

public interface InfoAccountListDao {

    @Insert({
            "insert into info_account_list (source, id, ",
            "aid, text)",
            "values (#{source,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
            "#{aid,jdbcType=VARCHAR}, #{text,jdbcType=VARCHAR})",
            "ON DUPLICATE KEY",
            "UPDATE `text`=#{newText,jdbcType=VARCHAR}"
    })
    int insertOrUpdate(InfoServiceImpl.UpdateInfoAccountList infoAccountList);

}
