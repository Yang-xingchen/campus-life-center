package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.Role;
import campuslifecenter.usercenter.entry.RoleExample;
import campuslifecenter.usercenter.entry.RoleKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    @Delete({
        "delete from role",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(RoleKey key);

    @Insert({
        "insert into role (aid, oid, ",
        "role, role_name)",
        "values (#{aid,jdbcType=VARCHAR}, #{oid,jdbcType=INTEGER}, ",
        "#{role,jdbcType=INTEGER}, #{roleName,jdbcType=VARCHAR})"
    })
    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExampleWithRowbounds(RoleExample example, RowBounds rowBounds);

    List<Role> selectByExample(RoleExample example);

    @Select({
        "select",
        "aid, oid, role, role_name",
        "from role",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.RoleMapper.BaseResultMap")
    Role selectByPrimaryKey(RoleKey key);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    @Update({
        "update role",
        "set role_name = #{roleName,jdbcType=VARCHAR}",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}",
          "and role = #{role,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Role record);
}