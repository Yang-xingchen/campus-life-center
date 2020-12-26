package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.entry.OrganizationExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface OrganizationMapper {
    long countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    @Delete({
        "delete from organization",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into organization (parent, type, ",
        "creator, name, visibility, ",
        "create_data)",
        "values (#{parent,jdbcType=INTEGER}, #{type,jdbcType=VARCHAR}, ",
        "#{creator,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{visibility,jdbcType=INTEGER}, ",
        "#{createData,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExampleWithRowbounds(OrganizationExample example, RowBounds rowBounds);

    List<Organization> selectByExample(OrganizationExample example);

    @Select({
        "select",
        "id, parent, type, creator, name, visibility, create_data",
        "from organization",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.OrganizationMapper.BaseResultMap")
    Organization selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByPrimaryKeySelective(Organization record);

    @Update({
        "update organization",
        "set parent = #{parent,jdbcType=INTEGER},",
          "type = #{type,jdbcType=VARCHAR},",
          "creator = #{creator,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "visibility = #{visibility,jdbcType=INTEGER},",
          "create_data = #{createData,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Organization record);
}