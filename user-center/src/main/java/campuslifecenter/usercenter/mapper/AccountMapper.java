package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.AccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    @Delete({
        "delete from account",
        "where sign_id = #{signId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String signId);

    @Insert({
        "insert into account (name, password, ",
        "gender, create_data, ",
        "security_key)",
        "values (#{name,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{gender,jdbcType=TINYINT}, #{createData,jdbcType=TIMESTAMP}, ",
        "#{securityKey,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="signId", before=false, resultType=String.class)
    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExampleWithRowbounds(AccountExample example, RowBounds rowBounds);

    List<Account> selectByExample(AccountExample example);

    @Select({
        "select",
        "sign_id, name, password, gender, create_data, security_key",
        "from account",
        "where sign_id = #{signId,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.AccountMapper.BaseResultMap")
    Account selectByPrimaryKey(String signId);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    @Update({
        "update account",
        "set name = #{name,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=TINYINT},",
          "create_data = #{createData,jdbcType=TIMESTAMP},",
          "security_key = #{securityKey,jdbcType=VARCHAR}",
        "where sign_id = #{signId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Account record);
}