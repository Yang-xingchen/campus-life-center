package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeTagExample;
import campuslifecenter.notice.entry.NoticeTagKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NoticeTagMapper {
    long countByExample(NoticeTagExample example);

    int deleteByExample(NoticeTagExample example);

    @Delete({
        "delete from notice_tag",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tag = #{tag,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(NoticeTagKey key);

    @Insert({
        "insert into notice_tag (nid, tag)",
        "values (#{nid,jdbcType=BIGINT}, #{tag,jdbcType=VARCHAR})"
    })
    int insert(NoticeTagKey record);

    int insertSelective(NoticeTagKey record);

    List<NoticeTagKey> selectByExampleWithRowbounds(NoticeTagExample example, RowBounds rowBounds);

    List<NoticeTagKey> selectByExample(NoticeTagExample example);

    int updateByExampleSelective(@Param("record") NoticeTagKey record, @Param("example") NoticeTagExample example);

    int updateByExample(@Param("record") NoticeTagKey record, @Param("example") NoticeTagExample example);
}