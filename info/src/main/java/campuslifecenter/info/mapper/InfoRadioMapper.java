package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.InfoRadioExample;
import campuslifecenter.info.entry.InfoRadioKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface InfoRadioMapper {
    long countByExample(InfoRadioExample example);

    int deleteByExample(InfoRadioExample example);

    @Delete({
        "delete from info_radio",
        "where id = #{id,jdbcType=BIGINT}",
          "and text = #{text,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(InfoRadioKey key);

    @Insert({
        "insert into info_radio (id, text)",
        "values (#{id,jdbcType=BIGINT}, #{text,jdbcType=VARCHAR})"
    })
    int insert(InfoRadioKey record);

    int insertSelective(InfoRadioKey record);

    List<InfoRadioKey> selectByExampleWithRowbounds(InfoRadioExample example, RowBounds rowBounds);

    List<InfoRadioKey> selectByExample(InfoRadioExample example);

    int updateByExampleSelective(@Param("record") InfoRadioKey record, @Param("example") InfoRadioExample example);

    int updateByExample(@Param("record") InfoRadioKey record, @Param("example") InfoRadioExample example);
}