package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoFile;
import campuslifecenter.info.entry.InfoFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoFileMapper {
    long countByExample(InfoFileExample example);

    int deleteByExample(InfoFileExample example);

    @Delete({
        "delete from info_file",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into info_file (id, path)",
        "values (#{id,jdbcType=BIGINT}, #{path,jdbcType=VARCHAR})"
    })
    int insert(InfoFile record);

    int insertSelective(InfoFile record);

    List<InfoFile> selectByExampleWithRowbounds(InfoFileExample example, RowBounds rowBounds);

    List<InfoFile> selectByExample(InfoFileExample example);

    @Select({
        "select",
        "id, path",
        "from info_file",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoFileMapper.BaseResultMap")
    InfoFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InfoFile record, @Param("example") InfoFileExample example);

    int updateByExample(@Param("record") InfoFile record, @Param("example") InfoFileExample example);

    int updateByPrimaryKeySelective(InfoFile record);

    @Update({
        "update info_file",
        "set path = #{path,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(InfoFile record);
}