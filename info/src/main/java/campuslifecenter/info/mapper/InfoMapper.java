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
        "insert into info (name, type, ",
        "persistent_source, default_visibility)",
        "values (#{name,jdbcType=VARCHAR}, #{type,jdbcType=INTEGER}, ",
        "#{persistentSource,jdbcType=VARCHAR}, #{defaultVisibility,jdbcType=INTEGER})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Info record);

    int insertSelective(Info record);

    List<Info> selectByExampleWithRowbounds(InfoExample example, RowBounds rowBounds);

    List<Info> selectByExample(InfoExample example);

    @Select({
        "select",
        "id, name, type, persistent_source, default_visibility",
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
          "type = #{type,jdbcType=INTEGER},",
          "persistent_source = #{persistentSource,jdbcType=VARCHAR},",
          "default_visibility = #{defaultVisibility,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Info record);
}