package campuslifecenter.info.dao;

import campuslifecenter.info.service.impl.AccountInfoServiceImpl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InfoDao {

    @Insert({
            "insert into account_submit ",
            "(ref, id, aid, multiple_index, text)",
            "values (#{ref,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
            "#{aid,jdbcType=VARCHAR}, #{multipleIndex,jdbcType=INTEGER}, #{text,jdbcType=VARCHAR})",
            "ON DUPLICATE KEY",
            "UPDATE `text`=#{newText,jdbcType=VARCHAR}"
    })
    int insertOrUpdate(AccountInfoServiceImpl.UpdateSubmit infoAccountList);

    @Select("SELECT id FROM info WHERE `hide`=0")
    List<Long> infosId();

}
