package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeInfoExample;
import campuslifecenter.notice.entry.NoticeInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NoticeInfoMapper {
    long countByExample(NoticeInfoExample example);

    int deleteByExample(NoticeInfoExample example);

    @Delete({
        "delete from notice_info",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(NoticeInfoKey key);

    @Insert({
        "insert into notice_info (nid, ref)",
        "values (#{nid,jdbcType=BIGINT}, #{ref,jdbcType=VARCHAR})"
    })
    int insert(NoticeInfoKey record);

    int insertSelective(NoticeInfoKey record);

    List<NoticeInfoKey> selectByExampleWithRowbounds(NoticeInfoExample example, RowBounds rowBounds);

    List<NoticeInfoKey> selectByExample(NoticeInfoExample example);

    int updateByExampleSelective(@Param("record") NoticeInfoKey record, @Param("example") NoticeInfoExample example);

    int updateByExample(@Param("record") NoticeInfoKey record, @Param("example") NoticeInfoExample example);
}