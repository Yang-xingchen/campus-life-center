package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSaveInfoExample;
import campuslifecenter.info.entry.AccountSaveInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountSaveInfoMapper {
    long countByExample(AccountSaveInfoExample example);

    int deleteByExample(AccountSaveInfoExample example);

    @Delete({
        "delete from account_save_info",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountSaveInfoKey key);

    @Insert({
        "insert into account_save_info (aid, id, ",
        "multiple_index, content, ",
        "code, visibility)",
        "values (#{aid,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
        "#{multipleIndex,jdbcType=INTEGER}, #{content,jdbcType=VARCHAR}, ",
        "#{code,jdbcType=BIT}, #{visibility,jdbcType=INTEGER})"
    })
    int insert(AccountSaveInfo record);

    int insertSelective(AccountSaveInfo record);

    List<AccountSaveInfo> selectByExampleWithRowbounds(AccountSaveInfoExample example, RowBounds rowBounds);

    List<AccountSaveInfo> selectByExample(AccountSaveInfoExample example);

    @Select({
        "select",
        "aid, id, multiple_index, content, code, visibility",
        "from account_save_info",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.info.mapper.AccountSaveInfoMapper.BaseResultMap")
    AccountSaveInfo selectByPrimaryKey(AccountSaveInfoKey key);

    int updateByExampleSelective(@Param("record") AccountSaveInfo record, @Param("example") AccountSaveInfoExample example);

    int updateByExample(@Param("record") AccountSaveInfo record, @Param("example") AccountSaveInfoExample example);

    int updateByPrimaryKeySelective(AccountSaveInfo record);

    @Update({
        "update account_save_info",
        "set content = #{content,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=BIT},",
          "visibility = #{visibility,jdbcType=INTEGER}",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccountSaveInfo record);
}