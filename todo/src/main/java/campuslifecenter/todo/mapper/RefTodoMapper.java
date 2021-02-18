package campuslifecenter.todo.mapper;

import campuslifecenter.todo.entry.RefTodo;
import campuslifecenter.todo.entry.RefTodoExample;
import campuslifecenter.todo.entry.RefTodoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RefTodoMapper {
    long countByExample(RefTodoExample example);

    int deleteByExample(RefTodoExample example);

    @Delete({
        "delete from ref_todo",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(RefTodoKey key);

    @Insert({
        "insert into ref_todo (ref, id, ",
        "type)",
        "values (#{ref,jdbcType=VARCHAR}, #{id,jdbcType=BIGINT}, ",
        "#{type,jdbcType=INTEGER})"
    })
    int insert(RefTodo record);

    int insertSelective(RefTodo record);

    List<RefTodo> selectByExampleWithRowbounds(RefTodoExample example, RowBounds rowBounds);

    List<RefTodo> selectByExample(RefTodoExample example);

    @Select({
        "select",
        "ref, id, type",
        "from ref_todo",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.todo.mapper.RefTodoMapper.BaseResultMap")
    RefTodo selectByPrimaryKey(RefTodoKey key);

    int updateByExampleSelective(@Param("record") RefTodo record, @Param("example") RefTodoExample example);

    int updateByExample(@Param("record") RefTodo record, @Param("example") RefTodoExample example);

    int updateByPrimaryKeySelective(RefTodo record);

    @Update({
        "update ref_todo",
        "set type = #{type,jdbcType=INTEGER}",
        "where ref = #{ref,jdbcType=VARCHAR}",
          "and id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RefTodo record);
}