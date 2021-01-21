package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoComposite;
import campuslifecenter.info.entry.InfoCompositeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoCompositeMapper {
    long countByExample(InfoCompositeExample example);

    int deleteByExample(InfoCompositeExample example);

    @Delete({
        "delete from info_composite",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into info_composite (id, pid)",
        "values (#{id,jdbcType=BIGINT}, #{pid,jdbcType=BIGINT})"
    })
    int insert(InfoComposite record);

    int insertSelective(InfoComposite record);

    List<InfoComposite> selectByExampleWithRowbounds(InfoCompositeExample example, RowBounds rowBounds);

    List<InfoComposite> selectByExample(InfoCompositeExample example);

    @Select({
        "select",
        "id, pid",
        "from info_composite",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoCompositeMapper.BaseResultMap")
    InfoComposite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InfoComposite record, @Param("example") InfoCompositeExample example);

    int updateByExample(@Param("record") InfoComposite record, @Param("example") InfoCompositeExample example);

    int updateByPrimaryKeySelective(InfoComposite record);

    @Update({
        "update info_composite",
        "set pid = #{pid,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(InfoComposite record);
}