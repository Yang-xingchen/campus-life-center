package campuslifecenter.todo.mapper;

import campuslifecenter.todo.entry.Todo;
import campuslifecenter.todo.entry.TodoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface TodoMapper {
    long countByExample(TodoExample example);

    int deleteByExample(TodoExample example);

    @Delete({
        "delete from todo",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into todo (source, title)",
        "values (#{source,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Todo record);

    int insertSelective(Todo record);

    List<Todo> selectByExampleWithRowbounds(TodoExample example, RowBounds rowBounds);

    List<Todo> selectByExample(TodoExample example);

    @Select({
        "select",
        "id, source, title",
        "from todo",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.todo.mapper.TodoMapper.BaseResultMap")
    Todo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Todo record, @Param("example") TodoExample example);

    int updateByExample(@Param("record") Todo record, @Param("example") TodoExample example);

    int updateByPrimaryKeySelective(Todo record);

    @Update({
        "update todo",
        "set source = #{source,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Todo record);
}