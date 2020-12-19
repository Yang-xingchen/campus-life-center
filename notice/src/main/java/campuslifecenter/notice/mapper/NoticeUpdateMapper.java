package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeUpdate;
import campuslifecenter.notice.entry.NoticeUpdateExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeUpdateMapper {
    long countByExample(NoticeUpdateExample example);

    int deleteByExample(NoticeUpdateExample example);

    @Delete({
        "delete from notice_update",
        "where uid = #{uid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer uid);

    @Insert({
        "insert into notice_update (nid, update_time, ",
        "title, importance, ",
        "time, content)",
        "values (#{nid,jdbcType=INTEGER}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{title,jdbcType=VARCHAR}, #{importance,jdbcType=TINYINT}, ",
        "#{time,jdbcType=TIMESTAMP}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="uid", before=false, resultType=Integer.class)
    int insert(NoticeUpdate record);

    int insertSelective(NoticeUpdate record);

    List<NoticeUpdate> selectByExampleWithBLOBsWithRowbounds(NoticeUpdateExample example, RowBounds rowBounds);

    List<NoticeUpdate> selectByExampleWithBLOBs(NoticeUpdateExample example);

    List<NoticeUpdate> selectByExampleWithRowbounds(NoticeUpdateExample example, RowBounds rowBounds);

    List<NoticeUpdate> selectByExample(NoticeUpdateExample example);

    @Select({
        "select",
        "uid, nid, update_time, title, importance, time, content",
        "from notice_update",
        "where uid = #{uid,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeUpdateMapper.ResultMapWithBLOBs")
    NoticeUpdate selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") NoticeUpdate record, @Param("example") NoticeUpdateExample example);

    int updateByExampleWithBLOBs(@Param("record") NoticeUpdate record, @Param("example") NoticeUpdateExample example);

    int updateByExample(@Param("record") NoticeUpdate record, @Param("example") NoticeUpdateExample example);

    int updateByPrimaryKeySelective(NoticeUpdate record);

    @Update({
        "update notice_update",
        "set nid = #{nid,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=TINYINT},",
          "time = #{time,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where uid = #{uid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(NoticeUpdate record);

    @Update({
        "update notice_update",
        "set nid = #{nid,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "title = #{title,jdbcType=VARCHAR},",
          "importance = #{importance,jdbcType=TINYINT},",
          "time = #{time,jdbcType=TIMESTAMP}",
        "where uid = #{uid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(NoticeUpdate record);
}