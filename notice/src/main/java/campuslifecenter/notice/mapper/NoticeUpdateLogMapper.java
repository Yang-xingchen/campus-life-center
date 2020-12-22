package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeUpdateLog;
import campuslifecenter.notice.entry.NoticeUpdateLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeUpdateLogMapper {
    long countByExample(NoticeUpdateLogExample example);

    int deleteByExample(NoticeUpdateLogExample example);

    @Delete({
        "delete from notice_update_log",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long uid);

    @Insert({
        "insert into notice_update_log (nid, update_time, ",
        "title, importance, ",
        "notice_time, content)",
        "values (#{nid,jdbcType=BIGINT}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{title,jdbcType=VARCHAR}, #{importance,jdbcType=INTEGER}, ",
        "#{noticeTime,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="uid", before=false, resultType=Long.class)
    int insert(NoticeUpdateLog record);

    int insertSelective(NoticeUpdateLog record);

    List<NoticeUpdateLog> selectByExampleWithBLOBsWithRowbounds(NoticeUpdateLogExample example, RowBounds rowBounds);

    List<NoticeUpdateLog> selectByExampleWithBLOBs(NoticeUpdateLogExample example);

    List<NoticeUpdateLog> selectByExampleWithRowbounds(NoticeUpdateLogExample example, RowBounds rowBounds);

    List<NoticeUpdateLog> selectByExample(NoticeUpdateLogExample example);

    @Select({
        "select",
        "uid, nid, update_time, title, importance, notice_time, content",
        "from notice_update_log",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeUpdateLogMapper.ResultMapWithBLOBs")
    NoticeUpdateLog selectByPrimaryKey(Long uid);

    int updateByExampleSelective(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByExampleWithBLOBs(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByExample(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByPrimaryKeySelective(NoticeUpdateLog record);

    @Update({
        "update notice_update_log",
        "set nid = #{nid,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=INTEGER},",
          "notice_time = #{noticeTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(NoticeUpdateLog record);

    @Update({
        "update notice_update_log",
        "set nid = #{nid,jdbcType=BIGINT},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=INTEGER},",
          "notice_time = #{noticeTime,jdbcType=TIMESTAMP}",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(NoticeUpdateLog record);
}