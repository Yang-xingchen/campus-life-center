package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoList;
import campuslifecenter.info.entry.InfoListExample;
import campuslifecenter.info.entry.InfoListKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoListMapper {
    long countByExample(InfoListExample example);

    int deleteByExample(InfoListExample example);

    @Delete({
        "delete from info_list",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(InfoListKey key);

    @Insert({
        "insert into info_list (ref, id, ",
        "list_order)",
        "values (#{ref,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
        "#{listOrder,jdbcType=INTEGER})"
    })
    int insert(InfoList record);

    int insertSelective(InfoList record);

    List<InfoList> selectByExampleWithRowbounds(InfoListExample example, RowBounds rowBounds);

    List<InfoList> selectByExample(InfoListExample example);

    @Select({
        "select",
        "ref, id, list_order",
        "from info_list",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoListMapper.BaseResultMap")
    InfoList selectByPrimaryKey(InfoListKey key);

    int updateByExampleSelective(@Param("record") InfoList record, @Param("example") InfoListExample example);

    int updateByExample(@Param("record") InfoList record, @Param("example") InfoListExample example);

    int updateByPrimaryKeySelective(InfoList record);

    @Update({
        "update info_list",
        "set list_order = #{listOrder,jdbcType=INTEGER}",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(InfoList record);
}