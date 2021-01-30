package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.entry.AccountSubmitExample;
import campuslifecenter.info.entry.AccountSubmitKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountSubmitMapper {
    long countByExample(AccountSubmitExample example);

    int deleteByExample(AccountSubmitExample example);

    @Delete({
        "delete from account_submit",
        "where root = #{root,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountSubmitKey key);

    @Insert({
        "insert into account_submit (root, id, ",
        "aid, multiple_index, ",
        "text)",
        "values (#{root,jdbcType=BIGINT}, #{id,jdbcType=BIGINT}, ",
        "#{aid,jdbcType=VARCHAR}, #{multipleIndex,jdbcType=INTEGER}, ",
        "#{text,jdbcType=VARCHAR})"
    })
    int insert(AccountSubmit record);

    int insertSelective(AccountSubmit record);

    List<AccountSubmit> selectByExampleWithRowbounds(AccountSubmitExample example, RowBounds rowBounds);

    List<AccountSubmit> selectByExample(AccountSubmitExample example);

    @Select({
        "select",
        "root, id, aid, multiple_index, text",
        "from account_submit",
        "where root = #{root,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.info.mapper.AccountSubmitMapper.BaseResultMap")
    AccountSubmit selectByPrimaryKey(AccountSubmitKey key);

    int updateByExampleSelective(@Param("record") AccountSubmit record, @Param("example") AccountSubmitExample example);

    int updateByExample(@Param("record") AccountSubmit record, @Param("example") AccountSubmitExample example);

    int updateByPrimaryKeySelective(AccountSubmit record);

    @Update({
        "update account_submit",
        "set text = #{text,jdbcType=VARCHAR}",
        "where root = #{root,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AccountSubmit record);
}