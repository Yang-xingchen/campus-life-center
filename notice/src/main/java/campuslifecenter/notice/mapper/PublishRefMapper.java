package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.PublishRef;
import campuslifecenter.notice.entry.PublishRefExample;
import campuslifecenter.notice.entry.PublishRefKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PublishRefMapper {
    long countByExample(PublishRefExample example);

    int deleteByExample(PublishRefExample example);

    @Delete({
        "delete from publish_ref",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and type = #{type,jdbcType=INTEGER}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(PublishRefKey key);

    @Insert({
        "insert into publish_ref (nid, type, ",
        "ref, finish)",
        "values (#{nid,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, ",
        "#{ref,jdbcType=VARCHAR}, #{finish,jdbcType=BIT})"
    })
    int insert(PublishRef record);

    int insertSelective(PublishRef record);

    List<PublishRef> selectByExampleWithRowbounds(PublishRefExample example, RowBounds rowBounds);

    List<PublishRef> selectByExample(PublishRefExample example);

    @Select({
        "select",
        "nid, type, ref, finish",
        "from publish_ref",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and type = #{type,jdbcType=INTEGER}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.notice.mapper.PublishRefMapper.BaseResultMap")
    PublishRef selectByPrimaryKey(PublishRefKey key);

    int updateByExampleSelective(@Param("record") PublishRef record, @Param("example") PublishRefExample example);

    int updateByExample(@Param("record") PublishRef record, @Param("example") PublishRefExample example);

    int updateByPrimaryKeySelective(PublishRef record);

    @Update({
        "update publish_ref",
        "set finish = #{finish,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and type = #{type,jdbcType=INTEGER}",
          "and ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(PublishRef record);
}