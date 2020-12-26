package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.AccountOrganizationExample;
import campuslifecenter.usercenter.entry.AccountOrganizationKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountOrganizationMapper {
    long countByExample(AccountOrganizationExample example);

    int deleteByExample(AccountOrganizationExample example);

    @Delete({
        "delete from account_organization",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountOrganizationKey key);

    @Insert({
        "insert into account_organization (aid, oid, ",
        "role, role_name)",
        "values (#{aid,jdbcType=VARCHAR}, #{oid,jdbcType=INTEGER}, ",
        "#{role,jdbcType=INTEGER}, #{roleName,jdbcType=VARCHAR})"
    })
    int insert(AccountOrganization record);

    int insertSelective(AccountOrganization record);

    List<AccountOrganization> selectByExampleWithRowbounds(AccountOrganizationExample example, RowBounds rowBounds);

    List<AccountOrganization> selectByExample(AccountOrganizationExample example);

    @Select({
        "select",
        "aid, oid, role, role_name",
        "from account_organization",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.AccountOrganizationMapper.BaseResultMap")
    AccountOrganization selectByPrimaryKey(AccountOrganizationKey key);

    int updateByExampleSelective(@Param("record") AccountOrganization record, @Param("example") AccountOrganizationExample example);

    int updateByExample(@Param("record") AccountOrganization record, @Param("example") AccountOrganizationExample example);

    int updateByPrimaryKeySelective(AccountOrganization record);

    @Update({
        "update account_organization",
        "set role_name = #{roleName,jdbcType=VARCHAR}",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccountOrganization record);
}