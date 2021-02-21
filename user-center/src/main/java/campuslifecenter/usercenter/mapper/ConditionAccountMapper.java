package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.ConditionAccountExample;
import campuslifecenter.usercenter.entry.ConditionAccountKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ConditionAccountMapper {
    long countByExample(ConditionAccountExample example);

    int deleteByExample(ConditionAccountExample example);

    @Delete({
        "delete from condition_account",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(ConditionAccountKey key);

    @Insert({
        "insert into condition_account (ref, aid)",
        "values (#{ref,jdbcType=VARCHAR}, #{aid,jdbcType=VARCHAR})"
    })
    int insert(ConditionAccountKey record);

    int insertSelective(ConditionAccountKey record);

    List<ConditionAccountKey> selectByExampleWithRowbounds(ConditionAccountExample example, RowBounds rowBounds);

    List<ConditionAccountKey> selectByExample(ConditionAccountExample example);

    int updateByExampleSelective(@Param("record") ConditionAccountKey record, @Param("example") ConditionAccountExample example);

    int updateByExample(@Param("record") ConditionAccountKey record, @Param("example") ConditionAccountExample example);
}