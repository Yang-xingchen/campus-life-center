package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeMate;
import campuslifecenter.notice.entry.NoticeMateExample;
import campuslifecenter.notice.entry.NoticeMateKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeMateMapper {
    long countByExample(NoticeMateExample example);

    int deleteByExample(NoticeMateExample example);

    @Delete({
        "delete from notice_mate",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and type = #{type,jdbcType=TINYINT}"
    })
    int deleteByPrimaryKey(NoticeMateKey key);

    @Insert({
        "insert into notice_mate (nid, type, ",
        "value)",
        "values (#{nid,jdbcType=INTEGER}, #{type,jdbcType=TINYINT}, ",
        "#{value,jdbcType=VARCHAR})"
    })
    int insert(NoticeMate record);

    int insertSelective(NoticeMate record);

    List<NoticeMate> selectByExampleWithRowbounds(NoticeMateExample example, RowBounds rowBounds);

    List<NoticeMate> selectByExample(NoticeMateExample example);

    @Select({
        "select",
        "nid, type, value",
        "from notice_mate",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and type = #{type,jdbcType=TINYINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeMateMapper.BaseResultMap")
    NoticeMate selectByPrimaryKey(NoticeMateKey key);

    int updateByExampleSelective(@Param("record") NoticeMate record, @Param("example") NoticeMateExample example);

    int updateByExample(@Param("record") NoticeMate record, @Param("example") NoticeMateExample example);

    int updateByPrimaryKeySelective(NoticeMate record);

    @Update({
        "update notice_mate",
        "set value = #{value,jdbcType=VARCHAR}",
        "where nid = #{nid,jdbcType=INTEGER}",
          "and type = #{type,jdbcType=TINYINT}"
    })
    int updateByPrimaryKey(NoticeMate record);
}