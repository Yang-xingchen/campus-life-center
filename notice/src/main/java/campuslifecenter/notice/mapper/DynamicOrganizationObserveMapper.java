package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.DynamicOrganizationObserve;
import campuslifecenter.notice.entry.DynamicOrganizationObserveExample;
import campuslifecenter.notice.entry.DynamicOrganizationObserveKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface DynamicOrganizationObserveMapper {
    long countByExample(DynamicOrganizationObserveExample example);

    int deleteByExample(DynamicOrganizationObserveExample example);

    @Delete({
        "delete from dynamic_organization_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(DynamicOrganizationObserveKey key);

    @Insert({
        "insert into dynamic_organization_observe (nid, oid, ",
        "is_belong, is_subscribe)",
        "values (#{nid,jdbcType=BIGINT}, #{oid,jdbcType=INTEGER}, ",
        "#{isBelong,jdbcType=BIT}, #{isSubscribe,jdbcType=BIT})"
    })
    int insert(DynamicOrganizationObserve record);

    int insertSelective(DynamicOrganizationObserve record);

    List<DynamicOrganizationObserve> selectByExampleWithRowbounds(DynamicOrganizationObserveExample example, RowBounds rowBounds);

    List<DynamicOrganizationObserve> selectByExample(DynamicOrganizationObserveExample example);

    @Select({
        "select",
        "nid, oid, is_belong, is_subscribe",
        "from dynamic_organization_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.DynamicOrganizationObserveMapper.BaseResultMap")
    DynamicOrganizationObserve selectByPrimaryKey(DynamicOrganizationObserveKey key);

    int updateByExampleSelective(@Param("record") DynamicOrganizationObserve record, @Param("example") DynamicOrganizationObserveExample example);

    int updateByExample(@Param("record") DynamicOrganizationObserve record, @Param("example") DynamicOrganizationObserveExample example);

    int updateByPrimaryKeySelective(DynamicOrganizationObserve record);

    @Update({
        "update dynamic_organization_observe",
        "set is_belong = #{isBelong,jdbcType=BIT},",
          "is_subscribe = #{isSubscribe,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DynamicOrganizationObserve record);
}