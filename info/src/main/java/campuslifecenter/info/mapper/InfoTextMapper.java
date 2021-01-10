package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoText;
import campuslifecenter.info.entry.InfoTextExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoTextMapper {
    long countByExample(InfoTextExample example);

    int deleteByExample(InfoTextExample example);

    @Delete({
        "delete from info_text",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into info_text (id, sample)",
        "values (#{id,jdbcType=BIGINT}, #{sample,jdbcType=VARCHAR})"
    })
    int insert(InfoText record);

    int insertSelective(InfoText record);

    List<InfoText> selectByExampleWithRowbounds(InfoTextExample example, RowBounds rowBounds);

    List<InfoText> selectByExample(InfoTextExample example);

    @Select({
        "select",
        "id, sample",
        "from info_text",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoTextMapper.BaseResultMap")
    InfoText selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InfoText record, @Param("example") InfoTextExample example);

    int updateByExample(@Param("record") InfoText record, @Param("example") InfoTextExample example);

    int updateByPrimaryKeySelective(InfoText record);

    @Update({
        "update info_text",
        "set sample = #{sample,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(InfoText record);
}