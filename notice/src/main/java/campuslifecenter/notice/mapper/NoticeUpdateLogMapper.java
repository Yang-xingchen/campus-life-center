package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeUpdateLog;
import campuslifecenter.notice.entry.NoticeUpdateLogExample;
import campuslifecenter.notice.entry.NoticeUpdateLogKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeUpdateLogMapper {
    long countByExample(NoticeUpdateLogExample example);

    int deleteByExample(NoticeUpdateLogExample example);

    @Delete({
        "delete from notice_update_log",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and version = #{version,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(NoticeUpdateLogKey key);

    @Insert({
        "insert into notice_update_log (nid, version, ",
        "update_time, title, ",
        "importance, notice_time, ",
        "content)",
        "values (#{nid,jdbcType=BIGINT}, #{version,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{title,jdbcType=VARCHAR}, ",
        "#{importance,jdbcType=INTEGER}, #{noticeTime,jdbcType=TIMESTAMP}, ",
        "#{content,jdbcType=LONGVARCHAR})"
    })
    int insert(NoticeUpdateLog record);

    int insertSelective(NoticeUpdateLog record);

    List<NoticeUpdateLog> selectByExampleWithBLOBsWithRowbounds(NoticeUpdateLogExample example, RowBounds rowBounds);

    List<NoticeUpdateLog> selectByExampleWithBLOBs(NoticeUpdateLogExample example);

    List<NoticeUpdateLog> selectByExampleWithRowbounds(NoticeUpdateLogExample example, RowBounds rowBounds);

    List<NoticeUpdateLog> selectByExample(NoticeUpdateLogExample example);

    @Select({
        "select",
        "nid, version, update_time, title, importance, notice_time, content",
        "from notice_update_log",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and version = #{version,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeUpdateLogMapper.ResultMapWithBLOBs")
    NoticeUpdateLog selectByPrimaryKey(NoticeUpdateLogKey key);

    int updateByExampleSelective(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByExampleWithBLOBs(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByExample(@Param("record") NoticeUpdateLog record, @Param("example") NoticeUpdateLogExample example);

    int updateByPrimaryKeySelective(NoticeUpdateLog record);

    @Update({
        "update notice_update_log",
        "set update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=INTEGER},",
          "notice_time = #{noticeTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and version = #{version,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(NoticeUpdateLog record);

    @Update({
        "update notice_update_log",
        "set update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=INTEGER},",
          "notice_time = #{noticeTime,jdbcType=TIMESTAMP}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and version = #{version,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(NoticeUpdateLog record);
}