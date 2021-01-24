package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.Info;
import campuslifecenter.info.entry.InfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoMapper {
    long countByExample(InfoExample example);

    int deleteByExample(InfoExample example);

    @Delete({
        "delete from info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into info (name, hide, ",
        "type, multiple, default_visibility)",
        "values (#{name,jdbcType=VARCHAR}, #{hide,jdbcType=BIT}, ",
        "#{type,jdbcType=INTEGER}, #{multiple,jdbcType=BIT}, #{defaultVisibility,jdbcType=INTEGER})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Info record);

    int insertSelective(Info record);

    List<Info> selectByExampleWithRowbounds(InfoExample example, RowBounds rowBounds);

    List<Info> selectByExample(InfoExample example);

    @Select({
        "select",
        "id, name, hide, type, multiple, default_visibility",
        "from info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoMapper.BaseResultMap")
    Info selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Info record, @Param("example") InfoExample example);

    int updateByExample(@Param("record") Info record, @Param("example") InfoExample example);

    int updateByPrimaryKeySelective(Info record);

    @Update({
        "update info",
        "set name = #{name,jdbcType=VARCHAR},",
          "hide = #{hide,jdbcType=BIT},",
          "type = #{type,jdbcType=INTEGER},",
          "multiple = #{multiple,jdbcType=BIT},",
          "default_visibility = #{defaultVisibility,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Info record);
}