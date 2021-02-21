package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.ConditionOrganization;
import campuslifecenter.usercenter.entry.ConditionOrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface ConditionOrganizationMapper {
    long countByExample(ConditionOrganizationExample example);

    int deleteByExample(ConditionOrganizationExample example);

    @Delete({
        "delete from condition_organization",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into condition_organization (ref, oid, ",
        "belong, subscribe)",
        "values (#{ref,jdbcType=VARCHAR}, #{oid,jdbcType=INTEGER}, ",
        "#{belong,jdbcType=BIT}, #{subscribe,jdbcType=BIT})"
    })
    int insert(ConditionOrganization record);

    int insertSelective(ConditionOrganization record);

    List<ConditionOrganization> selectByExampleWithRowbounds(ConditionOrganizationExample example, RowBounds rowBounds);

    List<ConditionOrganization> selectByExample(ConditionOrganizationExample example);

    @Select({
        "select",
        "ref, oid, belong, subscribe",
        "from condition_organization",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.ConditionOrganizationMapper.BaseResultMap")
    ConditionOrganization selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") ConditionOrganization record, @Param("example") ConditionOrganizationExample example);

    int updateByExample(@Param("record") ConditionOrganization record, @Param("example") ConditionOrganizationExample example);

    int updateByPrimaryKeySelective(ConditionOrganization record);

    @Update({
        "update condition_organization",
        "set oid = #{oid,jdbcType=INTEGER},",
          "belong = #{belong,jdbcType=BIT},",
          "subscribe = #{subscribe,jdbcType=BIT}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ConditionOrganization record);
}