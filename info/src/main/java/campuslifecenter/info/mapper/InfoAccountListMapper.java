package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoAccountList;
import campuslifecenter.info.entry.InfoAccountListExample;
import campuslifecenter.info.entry.InfoAccountListKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface InfoAccountListMapper {
    long countByExample(InfoAccountListExample example);

    int deleteByExample(InfoAccountListExample example);

    @Delete({
        "delete from info_account_list",
        "where source = #{source,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(InfoAccountListKey key);

    @Insert({
        "insert into info_account_list (source, id, ",
        "aid, text)",
        "values (#{source,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
        "#{aid,jdbcType=VARCHAR}, #{text,jdbcType=VARCHAR})"
    })
    int insert(InfoAccountList record);

    int insertSelective(InfoAccountList record);

    List<InfoAccountList> selectByExampleWithRowbounds(InfoAccountListExample example, RowBounds rowBounds);

    List<InfoAccountList> selectByExample(InfoAccountListExample example);

    @Select({
        "select",
        "source, id, aid, text",
        "from info_account_list",
        "where source = #{source,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.info.mapper.InfoAccountListMapper.BaseResultMap")
    InfoAccountList selectByPrimaryKey(InfoAccountListKey key);

    int updateByExampleSelective(@Param("record") InfoAccountList record, @Param("example") InfoAccountListExample example);

    int updateByExample(@Param("record") InfoAccountList record, @Param("example") InfoAccountListExample example);

    int updateByPrimaryKeySelective(InfoAccountList record);

    @Update({
        "update info_account_list",
        "set text = #{text,jdbcType=VARCHAR}",
        "where source = #{source,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(InfoAccountList record);
}