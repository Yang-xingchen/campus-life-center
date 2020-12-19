package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeButton;
import campuslifecenter.notice.entry.NoticeButtonExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeButtonMapper {
    long countByExample(NoticeButtonExample example);

    int deleteByExample(NoticeButtonExample example);

    @Delete({
        "delete from notice_button",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into notice_button (unfinished_value, finish_value)",
        "values (#{unfinishedValue,jdbcType=VARCHAR}, #{finishValue,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(NoticeButton record);

    int insertSelective(NoticeButton record);

    List<NoticeButton> selectByExampleWithRowbounds(NoticeButtonExample example, RowBounds rowBounds);

    List<NoticeButton> selectByExample(NoticeButtonExample example);

    @Select({
        "select",
        "id, unfinished_value, finish_value",
        "from notice_button",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeButtonMapper.BaseResultMap")
    NoticeButton selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoticeButton record, @Param("example") NoticeButtonExample example);

    int updateByExample(@Param("record") NoticeButton record, @Param("example") NoticeButtonExample example);

    int updateByPrimaryKeySelective(NoticeButton record);

    @Update({
        "update notice_button",
        "set unfinished_value = #{unfinishedValue,jdbcType=VARCHAR},",
          "finish_value = #{finishValue,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(NoticeButton record);
}