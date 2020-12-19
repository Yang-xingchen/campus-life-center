package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.RolePermissionExample;
import campuslifecenter.usercenter.entry.RolePermissionKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RolePermissionMapper {
    long countByExample(RolePermissionExample example);

    int deleteByExample(RolePermissionExample example);

    @Delete({
        "delete from role_permission",
        "where oid = #{oid,jdbcType=INTEGER}",
          "and rid = #{rid,jdbcType=INTEGER}",
          "and pid = #{pid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(RolePermissionKey key);

    @Insert({
        "insert into role_permission (oid, rid, ",
        "pid)",
        "values (#{oid,jdbcType=INTEGER}, #{rid,jdbcType=INTEGER}, ",
        "#{pid,jdbcType=INTEGER})"
    })
    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);

    List<RolePermissionKey> selectByExampleWithRowbounds(RolePermissionExample example, RowBounds rowBounds);

    List<RolePermissionKey> selectByExample(RolePermissionExample example);

    int updateByExampleSelective(@Param("record") RolePermissionKey record, @Param("example") RolePermissionExample example);

    int updateByExample(@Param("record") RolePermissionKey record, @Param("example") RolePermissionExample example);
}