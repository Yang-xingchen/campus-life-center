package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishInfoExample;
import campuslifecenter.notice.entry.PublishInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PublishInfoMapper {
    long countByExample(PublishInfoExample example);

    int deleteByExample(PublishInfoExample example);

    @Delete({
        "delete from publish_info",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=BIGINT}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(PublishInfoKey key);

    @Insert({
        "insert into publish_info (nid, ref, ",
        "iid, dynamic, type, ",
        "type_value)",
        "values (#{nid,jdbcType=BIGINT}, #{ref,jdbcType=BIGINT}, ",
        "#{iid,jdbcType=BIGINT}, #{dynamic,jdbcType=BIT}, #{type,jdbcType=INTEGER}, ",
        "#{typeValue,jdbcType=VARCHAR})"
    })
    int insert(PublishInfo record);

    int insertSelective(PublishInfo record);

    List<PublishInfo> selectByExampleWithRowbounds(PublishInfoExample example, RowBounds rowBounds);

    List<PublishInfo> selectByExample(PublishInfoExample example);

    @Select({
        "select",
        "nid, ref, iid, dynamic, type, type_value",
        "from publish_info",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=BIGINT}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.PublishInfoMapper.BaseResultMap")
    PublishInfo selectByPrimaryKey(PublishInfoKey key);

    int updateByExampleSelective(@Param("record") PublishInfo record, @Param("example") PublishInfoExample example);

    int updateByExample(@Param("record") PublishInfo record, @Param("example") PublishInfoExample example);

    int updateByPrimaryKeySelective(PublishInfo record);

    @Update({
        "update publish_info",
        "set dynamic = #{dynamic,jdbcType=BIT},",
          "type = #{type,jdbcType=INTEGER},",
          "type_value = #{typeValue,jdbcType=VARCHAR}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and ref = #{ref,jdbcType=BIGINT}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PublishInfo record);
}