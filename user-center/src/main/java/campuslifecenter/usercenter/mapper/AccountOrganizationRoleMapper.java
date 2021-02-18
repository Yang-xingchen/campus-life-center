package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.AccountOrganizationRoleExample;
import campuslifecenter.usercenter.entry.AccountOrganizationRoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AccountOrganizationRoleMapper {
    long countByExample(AccountOrganizationRoleExample example);

    int deleteByExample(AccountOrganizationRoleExample example);

    @Delete({
        "delete from account_organization_role",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountOrganizationRoleKey key);

    @Insert({
        "insert into account_organization_role (aid, oid, ",
        "id)",
        "values (#{aid,jdbcType=VARCHAR}, #{oid,jdbcType=INTEGER}, ",
        "#{id,jdbcType=INTEGER})"
    })
    int insert(AccountOrganizationRoleKey record);

    int insertSelective(AccountOrganizationRoleKey record);

    List<AccountOrganizationRoleKey> selectByExampleWithRowbounds(AccountOrganizationRoleExample example, RowBounds rowBounds);

    List<AccountOrganizationRoleKey> selectByExample(AccountOrganizationRoleExample example);

    int updateByExampleSelective(@Param("record") AccountOrganizationRoleKey record, @Param("example") AccountOrganizationRoleExample example);

    int updateByExample(@Param("record") AccountOrganizationRoleKey record, @Param("example") AccountOrganizationRoleExample example);
}