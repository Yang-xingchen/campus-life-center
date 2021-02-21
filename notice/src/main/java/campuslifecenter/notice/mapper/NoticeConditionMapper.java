package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeCondition;
import campuslifecenter.notice.entry.NoticeConditionExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeConditionMapper {
    long countByExample(NoticeConditionExample example);

    int deleteByExample(NoticeConditionExample example);

    @Delete({
        "delete from notice_condition",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into notice_condition (ref, nid, ",
        "type)",
        "values (#{ref,jdbcType=VARCHAR}, #{nid,jdbcType=BIGINT}, ",
        "#{type,jdbcType=INTEGER})"
    })
    int insert(NoticeCondition record);

    int insertSelective(NoticeCondition record);

    List<NoticeCondition> selectByExampleWithRowbounds(NoticeConditionExample example, RowBounds rowBounds);

    List<NoticeCondition> selectByExample(NoticeConditionExample example);

    @Select({
        "select",
        "ref, nid, type",
        "from notice_condition",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeConditionMapper.BaseResultMap")
    NoticeCondition selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") NoticeCondition record, @Param("example") NoticeConditionExample example);

    int updateByExample(@Param("record") NoticeCondition record, @Param("example") NoticeConditionExample example);

    int updateByPrimaryKeySelective(NoticeCondition record);

    @Update({
        "update notice_condition",
        "set nid = #{nid,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(NoticeCondition record);
}