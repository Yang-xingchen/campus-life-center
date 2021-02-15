package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.PublishAccountExample;
import campuslifecenter.notice.entry.PublishAccountKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PublishAccountMapper {
    long countByExample(PublishAccountExample example);

    int deleteByExample(PublishAccountExample example);

    @Delete({
        "delete from publish_account",
        "where id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(PublishAccountKey key);

    @Insert({
        "insert into publish_account (id, aid)",
        "values (#{id,jdbcType=BIGINT}, #{aid,jdbcType=VARCHAR})"
    })
    int insert(PublishAccountKey record);

    int insertSelective(PublishAccountKey record);

    List<PublishAccountKey> selectByExampleWithRowbounds(PublishAccountExample example, RowBounds rowBounds);

    List<PublishAccountKey> selectByExample(PublishAccountExample example);

    int updateByExampleSelective(@Param("record") PublishAccountKey record, @Param("example") PublishAccountExample example);

    int updateByExample(@Param("record") PublishAccountKey record, @Param("example") PublishAccountExample example);
}