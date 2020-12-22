package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeMapper {
    long countByExample(NoticeExample example);

    int deleteByExample(NoticeExample example);

    @Delete({
        "delete from notice",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into notice (creator, organization, ",
        "visibility, create_time, ",
        "importance, public_type, ",
        "title, start_time, ",
        "end_time, content)",
        "values (#{creator,jdbcType=VARCHAR}, #{organization,jdbcType=INTEGER}, ",
        "#{visibility,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{importance,jdbcType=INTEGER}, #{publicType,jdbcType=INTEGER}, ",
        "#{title,jdbcType=VARCHAR}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Notice record);

    int insertSelective(Notice record);

    List<Notice> selectByExampleWithBLOBsWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExampleWithBLOBs(NoticeExample example);

    List<Notice> selectByExampleWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExample(NoticeExample example);

    @Select({
        "select",
        "id, creator, organization, visibility, create_time, importance, public_type, ",
        "title, start_time, end_time, content",
        "from notice",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeMapper.ResultMapWithBLOBs")
    Notice selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByPrimaryKeySelective(Notice record);

    @Update({
        "update notice",
        "set creator = #{creator,jdbcType=VARCHAR},",
          "organization = #{organization,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "importance = #{importance,jdbcType=INTEGER},",
          "public_type = #{publicType,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Notice record);

    @Update({
        "update notice",
        "set creator = #{creator,jdbcType=VARCHAR},",
          "organization = #{organization,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "importance = #{importance,jdbcType=INTEGER},",
          "public_type = #{publicType,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Notice record);
}