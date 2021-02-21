package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.ConditionInfo;
import campuslifecenter.info.entry.ConditionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface ConditionInfoMapper {
    long countByExample(ConditionInfoExample example);

    int deleteByExample(ConditionInfoExample example);

    @Delete({
        "delete from condition_info",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into condition_info (ref, iid, ",
        "text, type)",
        "values (#{ref,jdbcType=VARCHAR}, #{iid,jdbcType=BIGINT}, ",
        "#{text,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER})"
    })
    int insert(ConditionInfo record);

    int insertSelective(ConditionInfo record);

    List<ConditionInfo> selectByExampleWithRowbounds(ConditionInfoExample example, RowBounds rowBounds);

    List<ConditionInfo> selectByExample(ConditionInfoExample example);

    @Select({
        "select",
        "ref, iid, text, type",
        "from condition_info",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.info.mapper.ConditionInfoMapper.BaseResultMap")
    ConditionInfo selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") ConditionInfo record, @Param("example") ConditionInfoExample example);

    int updateByExample(@Param("record") ConditionInfo record, @Param("example") ConditionInfoExample example);

    int updateByPrimaryKeySelective(ConditionInfo record);

    @Update({
        "update condition_info",
        "set iid = #{iid,jdbcType=BIGINT},",
          "text = #{text,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=INTEGER}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ConditionInfo record);
}