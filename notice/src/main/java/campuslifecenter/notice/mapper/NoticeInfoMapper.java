package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeInfo;
import campuslifecenter.notice.entry.NoticeInfoExample;
import campuslifecenter.notice.entry.NoticeInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
        "insert into notice_info (nid, ref, ",
        "root_id)",
        "values (#{nid,jdbcType=BIGINT}, #{ref,jdbcType=VARCHAR}, ",
        "#{rootId,jdbcType=BIGINT})"
    })
    int insert(NoticeInfo record);

    int insertSelective(NoticeInfo record);

    List<NoticeInfo> selectByExampleWithRowbounds(NoticeInfoExample example, RowBounds rowBounds);

    List<NoticeInfo> selectByExample(NoticeInfoExample example);

    @Select({
        "select",
        "nid, ref, root_id",
        "from notice_info",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeInfoMapper.BaseResultMap")
    NoticeInfo selectByPrimaryKey(NoticeInfoKey key);

    int updateByExampleSelective(@Param("record") NoticeInfo record, @Param("example") NoticeInfoExample example);

    int updateByExample(@Param("record") NoticeInfo record, @Param("example") NoticeInfoExample example);

    int updateByPrimaryKeySelective(NoticeInfo record);

    @Update({
        "update notice_info",
        "set root_id = #{rootId,jdbcType=BIGINT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(NoticeInfo record);
}