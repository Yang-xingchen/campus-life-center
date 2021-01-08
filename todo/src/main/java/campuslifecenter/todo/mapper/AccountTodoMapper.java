package campuslifecenter.todo.mapper;

import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.AccountTodoExample;
import campuslifecenter.todo.entry.AccountTodoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountTodoMapper {
    long countByExample(AccountTodoExample example);

    int deleteByExample(AccountTodoExample example);

    @Delete({
        "delete from account_todo",
        "where id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(AccountTodoKey key);

    @Insert({
        "insert into account_todo (id, aid, ",
        "finish, top, add_list)",
        "values (#{id,jdbcType=BIGINT}, #{aid,jdbcType=VARCHAR}, ",
        "#{finish,jdbcType=BIT}, #{top,jdbcType=BIT}, #{addList,jdbcType=BIT})"
    })
    int insert(AccountTodo record);

    int insertSelective(AccountTodo record);

    List<AccountTodo> selectByExampleWithRowbounds(AccountTodoExample example, RowBounds rowBounds);

    List<AccountTodo> selectByExample(AccountTodoExample example);

    @Select({
        "select",
        "id, aid, finish, top, add_list",
        "from account_todo",
        "where id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.todo.mapper.AccountTodoMapper.BaseResultMap")
    AccountTodo selectByPrimaryKey(AccountTodoKey key);

    int updateByExampleSelective(@Param("record") AccountTodo record, @Param("example") AccountTodoExample example);

    int updateByExample(@Param("record") AccountTodo record, @Param("example") AccountTodoExample example);

    int updateByPrimaryKeySelective(AccountTodo record);

    @Update({
        "update account_todo",
        "set finish = #{finish,jdbcType=BIT},",
          "top = #{top,jdbcType=BIT},",
          "add_list = #{addList,jdbcType=BIT}",
        "where id = #{id,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(AccountTodo record);
}