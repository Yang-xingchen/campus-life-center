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
        "publish_status, visibility, ",
        "importance, version, ",
        "title, content_type, ",
        "create_time, public_type, ",
        "start_time, end_time, ",
        "ref, content)",
        "values (#{creator,jdbcType=VARCHAR}, #{organization,jdbcType=INTEGER}, ",
        "#{publishStatus,jdbcType=INTEGER}, #{visibility,jdbcType=INTEGER}, ",
        "#{importance,jdbcType=INTEGER}, #{version,jdbcType=INTEGER}, ",
        "#{title,jdbcType=VARCHAR}, #{contentType,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{publicType,jdbcType=INTEGER}, ",
        "#{startTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP}, ",
        "#{ref,jdbcType=VARCHAR}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Notice record);

    int insertSelective(Notice record);

    List<Notice> selectByExampleWithBLOBsWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExampleWithBLOBs(NoticeExample example);

    List<Notice> selectByExampleWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExample(NoticeExample example);

    @Select({
        "select",
        "id, creator, organization, publish_status, visibility, importance, version, ",
        "title, content_type, create_time, public_type, start_time, end_time, ref, content",
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
          "publish_status = #{publishStatus,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=INTEGER},",
          "importance = #{importance,jdbcType=INTEGER},",
          "version = #{version,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "content_type = #{contentType,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "public_type = #{publicType,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "ref = #{ref,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Notice record);

    @Update({
        "update notice",
        "set creator = #{creator,jdbcType=VARCHAR},",
          "organization = #{organization,jdbcType=INTEGER},",
          "publish_status = #{publishStatus,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=INTEGER},",
          "importance = #{importance,jdbcType=INTEGER},",
          "version = #{version,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "content_type = #{contentType,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "public_type = #{publicType,jdbcType=INTEGER},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "ref = #{ref,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Notice record);
}