package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.AccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    @Delete({
        "delete from account",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Insert({
        "insert into account (id, name, ",
        "password, gender, ",
        "create_data)",
        "values (#{id,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{gender,jdbcType=INTEGER}, ",
        "#{createData,jdbcType=TIMESTAMP})"
    })
    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExampleWithRowbounds(AccountExample example, RowBounds rowBounds);

    List<Account> selectByExample(AccountExample example);

    @Select({
        "select",
        "id, name, password, gender, create_data",
        "from account",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.AccountMapper.BaseResultMap")
    Account selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    @Update({
        "update account",
        "set name = #{name,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=INTEGER},",
          "create_data = #{createData,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Account record);
}