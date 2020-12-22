package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.PermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    @Delete({
        "delete from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into permission (name)",
        "values (#{name,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExampleWithRowbounds(PermissionExample example, RowBounds rowBounds);

    List<Permission> selectByExample(PermissionExample example);

    @Select({
        "select",
        "id, name",
        "from permission",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.PermissionMapper.BaseResultMap")
    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    @Update({
        "update permission",
        "set name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Permission record);
}