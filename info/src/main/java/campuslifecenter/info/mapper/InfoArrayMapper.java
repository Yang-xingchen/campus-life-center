package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoArray;
import campuslifecenter.info.entry.InfoArrayExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoArrayMapper {
    long countByExample(InfoArrayExample example);

    int deleteByExample(InfoArrayExample example);

    @Delete({
        "delete from info_array",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into info_array (id, pid)",
        "values (#{id,jdbcType=BIGINT}, #{pid,jdbcType=BIGINT})"
    })
    int insert(InfoArray record);

    int insertSelective(InfoArray record);

    List<InfoArray> selectByExampleWithRowbounds(InfoArrayExample example, RowBounds rowBounds);

    List<InfoArray> selectByExample(InfoArrayExample example);

    @Select({
        "select",
        "id, pid",
        "from info_array",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoArrayMapper.BaseResultMap")
    InfoArray selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InfoArray record, @Param("example") InfoArrayExample example);

    int updateByExample(@Param("record") InfoArray record, @Param("example") InfoArrayExample example);

    int updateByPrimaryKeySelective(InfoArray record);

    @Update({
        "update info_array",
        "set pid = #{pid,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(InfoArray record);
}