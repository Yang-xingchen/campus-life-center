package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishOrganizationExample;
import campuslifecenter.notice.entry.PublishOrganizationKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PublishOrganizationMapper {
    long countByExample(PublishOrganizationExample example);

    int deleteByExample(PublishOrganizationExample example);

    @Delete({
        "delete from publish_organization",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(PublishOrganizationKey key);

    @Insert({
        "insert into publish_organization (nid, oid, ",
        "dynamic, belong, subscribe)",
        "values (#{nid,jdbcType=BIGINT}, #{oid,jdbcType=INTEGER}, ",
        "#{dynamic,jdbcType=BIT}, #{belong,jdbcType=BIT}, #{subscribe,jdbcType=BIT})"
    })
    int insert(PublishOrganization record);

    int insertSelective(PublishOrganization record);

    List<PublishOrganization> selectByExampleWithRowbounds(PublishOrganizationExample example, RowBounds rowBounds);

    List<PublishOrganization> selectByExample(PublishOrganizationExample example);

    @Select({
        "select",
        "nid, oid, dynamic, belong, subscribe",
        "from publish_organization",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.PublishOrganizationMapper.BaseResultMap")
    PublishOrganization selectByPrimaryKey(PublishOrganizationKey key);

    int updateByExampleSelective(@Param("record") PublishOrganization record, @Param("example") PublishOrganizationExample example);

    int updateByExample(@Param("record") PublishOrganization record, @Param("example") PublishOrganizationExample example);

    int updateByPrimaryKeySelective(PublishOrganization record);

    @Update({
        "update publish_organization",
        "set dynamic = #{dynamic,jdbcType=BIT},",
          "belong = #{belong,jdbcType=BIT},",
          "subscribe = #{subscribe,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PublishOrganization record);
}