package campuslifecenter.todo.mapper;

import campuslifecenter.todo.entry.ConditionTodo;
import campuslifecenter.todo.entry.ConditionTodoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface ConditionTodoMapper {
    long countByExample(ConditionTodoExample example);

    int deleteByExample(ConditionTodoExample example);

    @Delete({
        "delete from condition_todo",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into condition_todo (ref, tid, ",
        "finish, dynamic)",
        "values (#{ref,jdbcType=VARCHAR}, #{tid,jdbcType=BIGINT}, ",
        "#{finish,jdbcType=BIT}, #{dynamic,jdbcType=BIT})"
    })
    int insert(ConditionTodo record);

    int insertSelective(ConditionTodo record);

    List<ConditionTodo> selectByExampleWithRowbounds(ConditionTodoExample example, RowBounds rowBounds);

    List<ConditionTodo> selectByExample(ConditionTodoExample example);

    @Select({
        "select",
        "ref, tid, finish, dynamic",
        "from condition_todo",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.todo.mapper.ConditionTodoMapper.BaseResultMap")
    ConditionTodo selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") ConditionTodo record, @Param("example") ConditionTodoExample example);

    int updateByExample(@Param("record") ConditionTodo record, @Param("example") ConditionTodoExample example);

    int updateByPrimaryKeySelective(ConditionTodo record);

    @Update({
        "update condition_todo",
        "set tid = #{tid,jdbcType=BIGINT},",
          "finish = #{finish,jdbcType=BIT},",
          "dynamic = #{dynamic,jdbcType=BIT}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ConditionTodo record);
}