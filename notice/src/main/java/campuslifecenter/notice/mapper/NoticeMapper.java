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
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into notice (creator, organization, ",
        "visibility, public_type, ",
        "create_time, title, ",
        "importance, time, ",
        "content)",
        "values (#{creator,jdbcType=VARCHAR}, #{organization,jdbcType=INTEGER}, ",
        "#{visibility,jdbcType=TINYINT}, #{publicType,jdbcType=TINYINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{title,jdbcType=VARCHAR}, ",
        "#{importance,jdbcType=TINYINT}, #{time,jdbcType=TIMESTAMP}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Notice record);

    int insertSelective(Notice record);

    List<Notice> selectByExampleWithBLOBsWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExampleWithBLOBs(NoticeExample example);

    List<Notice> selectByExampleWithRowbounds(NoticeExample example, RowBounds rowBounds);

    List<Notice> selectByExample(NoticeExample example);

    @Select({
        "select",
        "id, creator, organization, visibility, public_type, create_time, title, importance, ",
        "time, content",
        "from notice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeMapper.ResultMapWithBLOBs")
    Notice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

    int updateByPrimaryKeySelective(Notice record);

    @Update({
        "update notice",
        "set creator = #{creator,jdbcType=VARCHAR},",
          "organization = #{organization,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=TINYINT},",
          "public_type = #{publicType,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=TINYINT},",
          "time = #{time,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Notice record);

    @Update({
        "update notice",
        "set creator = #{creator,jdbcType=VARCHAR},",
          "organization = #{organization,jdbcType=INTEGER},",
          "visibility = #{visibility,jdbcType=TINYINT},",
          "public_type = #{publicType,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=TINYINT},",
          "time = #{time,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Notice record);
}