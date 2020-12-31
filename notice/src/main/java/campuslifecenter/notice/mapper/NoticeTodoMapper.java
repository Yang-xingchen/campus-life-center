package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.NoticeTodo;
import campuslifecenter.notice.entry.NoticeTodoExample;
import campuslifecenter.notice.entry.NoticeTodoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface NoticeTodoMapper {
    long countByExample(NoticeTodoExample example);

    int deleteByExample(NoticeTodoExample example);

    @Delete({
        "delete from notice_todo",
        "where id = #{id,jdbcType=INTEGER}",
          "and nid = #{nid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(NoticeTodoKey key);

    @Insert({
        "insert into notice_todo (id, nid, ",
        "type, type_value)",
        "values (#{id,jdbcType=INTEGER}, #{nid,jdbcType=BIGINT}, ",
        "#{type,jdbcType=INTEGER}, #{typeValue,jdbcType=VARCHAR})"
    })
    int insert(NoticeTodo record);

    int insertSelective(NoticeTodo record);

    List<NoticeTodo> selectByExampleWithRowbounds(NoticeTodoExample example, RowBounds rowBounds);

    List<NoticeTodo> selectByExample(NoticeTodoExample example);

    @Select({
        "select",
        "id, nid, type, type_value",
        "from notice_todo",
        "where id = #{id,jdbcType=INTEGER}",
          "and nid = #{nid,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.NoticeTodoMapper.BaseResultMap")
    NoticeTodo selectByPrimaryKey(NoticeTodoKey key);

    int updateByExampleSelective(@Param("record") NoticeTodo record, @Param("example") NoticeTodoExample example);

    int updateByExample(@Param("record") NoticeTodo record, @Param("example") NoticeTodoExample example);

    int updateByPrimaryKeySelective(NoticeTodo record);

    @Update({
        "update notice_todo",
        "set type = #{type,jdbcType=INTEGER},",
          "type_value = #{typeValue,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}",
          "and nid = #{nid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(NoticeTodo record);
}