package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeInformationExample;
import campuslifecenter.notice.entry.NoticeInformationKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface NoticeInformationMapper {
    long countByExample(NoticeInformationExample example);

    int deleteByExample(NoticeInformationExample example);

    @Delete({
        "delete from notice_information",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and iid = #{iid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(NoticeInformationKey key);

    @Insert({
        "insert into notice_information (nid, iid)",
        "values (#{nid,jdbcType=INTEGER}, #{iid,jdbcType=INTEGER})"
    })
    int insert(NoticeInformationKey record);

    int insertSelective(NoticeInformationKey record);

    List<NoticeInformationKey> selectByExampleWithRowbounds(NoticeInformationExample example, RowBounds rowBounds);

    List<NoticeInformationKey> selectByExample(NoticeInformationExample example);

    int updateByExampleSelective(@Param("record") NoticeInformationKey record, @Param("example") NoticeInformationExample example);

    int updateByExample(@Param("record") NoticeInformationKey record, @Param("example") NoticeInformationExample example);
}