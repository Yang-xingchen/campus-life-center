package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.entry.PublishTodoExample;
import campuslifecenter.notice.entry.PublishTodoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface PublishTodoMapper {
    long countByExample(PublishTodoExample example);

    int deleteByExample(PublishTodoExample example);

    @Delete({
        "delete from publish_todo",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(PublishTodoKey key);

    @Insert({
        "insert into publish_todo (nid, tid, ",
        "dynamic, finish)",
        "values (#{nid,jdbcType=BIGINT}, #{tid,jdbcType=BIGINT}, ",
        "#{dynamic,jdbcType=BIT}, #{finish,jdbcType=BIT})"
    })
    int insert(PublishTodo record);

    int insertSelective(PublishTodo record);

    List<PublishTodo> selectByExampleWithRowbounds(PublishTodoExample example, RowBounds rowBounds);

    List<PublishTodo> selectByExample(PublishTodoExample example);

    @Select({
        "select",
        "nid, tid, dynamic, finish",
        "from publish_todo",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.PublishTodoMapper.BaseResultMap")
    PublishTodo selectByPrimaryKey(PublishTodoKey key);

    int updateByExampleSelective(@Param("record") PublishTodo record, @Param("example") PublishTodoExample example);

    int updateByExample(@Param("record") PublishTodo record, @Param("example") PublishTodoExample example);

    int updateByPrimaryKeySelective(PublishTodo record);

    @Update({
        "update publish_todo",
        "set dynamic = #{dynamic,jdbcType=BIT},",
          "finish = #{finish,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(PublishTodo record);
}