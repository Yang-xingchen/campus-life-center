package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.AccountInfo;
import campuslifecenter.info.entry.AccountInfoExample;
import campuslifecenter.info.entry.AccountInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountInfoMapper {
    long countByExample(AccountInfoExample example);

    int deleteByExample(AccountInfoExample example);

    @Delete({
        "delete from account_info",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountInfoKey key);

    @Insert({
        "insert into account_info (aid, id, ",
        "multiple_index, text, ",
        "code, visibility)",
        "values (#{aid,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
        "#{multipleIndex,jdbcType=INTEGER}, #{text,jdbcType=VARCHAR}, ",
        "#{code,jdbcType=BIT}, #{visibility,jdbcType=INTEGER})"
    })
    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    List<AccountInfo> selectByExampleWithRowbounds(AccountInfoExample example, RowBounds rowBounds);

    List<AccountInfo> selectByExample(AccountInfoExample example);

    @Select({
        "select",
        "aid, id, multiple_index, text, code, visibility",
        "from account_info",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.info.mapper.AccountInfoMapper.BaseResultMap")
    AccountInfo selectByPrimaryKey(AccountInfoKey key);

    int updateByExampleSelective(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    int updateByExample(@Param("record") AccountInfo record, @Param("example") AccountInfoExample example);

    int updateByPrimaryKeySelective(AccountInfo record);

    @Update({
        "update account_info",
        "set text = #{text,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=BIT},",
          "visibility = #{visibility,jdbcType=INTEGER}",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccountInfo record);
}