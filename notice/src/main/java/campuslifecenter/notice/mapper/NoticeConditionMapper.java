package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeCondition;
import campuslifecenter.notice.entry.NoticeConditionExample;
import campuslifecenter.notice.entry.NoticeConditionKey;
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
        "where nid = #{nid,jdbcType=INTEGER}",
          "and condition_type = #{conditionType,jdbcType=TINYINT}"
    })
    int deleteByPrimaryKey(NoticeConditionKey key);

    @Insert({
        "insert into notice_condition (nid, condition_type, ",
        "condition_key, condition_operation, ",
        "condition_value)",
        "values (#{nid,jdbcType=INTEGER}, #{conditionType,jdbcType=TINYINT}, ",
        "#{conditionKey,jdbcType=VARCHAR}, #{conditionOperation,jdbcType=TINYINT}, ",
        "#{conditionValue,jdbcType=VARCHAR})"
    })
    int insert(NoticeCondition record);

    int insertSelective(NoticeCondition record);

    List<NoticeCondition> selectByExampleWithRowbounds(NoticeConditionExample example, RowBounds rowBounds);

    List<NoticeCondition> selectByExample(NoticeConditionExample example);

    @Select({
        "select",
        "nid, condition_type, condition_key, condition_operation, condition_value",
        "from notice_condition",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and condition_type = #{conditionType,jdbcType=TINYINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeConditionMapper.BaseResultMap")
    NoticeCondition selectByPrimaryKey(NoticeConditionKey key);

    int updateByExampleSelective(@Param("record") NoticeCondition record, @Param("example") NoticeConditionExample example);

    int updateByExample(@Param("record") NoticeCondition record, @Param("example") NoticeConditionExample example);

    int updateByPrimaryKeySelective(NoticeCondition record);

    @Update({
        "update notice_condition",
        "set condition_key = #{conditionKey,jdbcType=VARCHAR},",
          "condition_operation = #{conditionOperation,jdbcType=TINYINT},",
          "condition_value = #{conditionValue,jdbcType=VARCHAR}",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and condition_type = #{conditionType,jdbcType=TINYINT}"
    })
    int updateByPrimaryKey(NoticeCondition record);
}