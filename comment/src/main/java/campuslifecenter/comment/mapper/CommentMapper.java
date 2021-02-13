package campuslifecenter.comment.mapper;

import campuslifecenter.comment.entry.Comment;
import campuslifecenter.comment.entry.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    @Delete({
        "delete from comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into comment (parent, aid, ",
        "publish_time, content)",
        "values (#{parent,jdbcType=BIGINT}, #{aid,jdbcType=VARCHAR}, ",
        "#{publishTime,jdbcType=TIMESTAMP}, #{content,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExampleWithRowbounds(CommentExample example, RowBounds rowBounds);

    List<Comment> selectByExample(CommentExample example);

    @Select({
        "select",
        "id, parent, aid, publish_time, content",
        "from comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.comment.mapper.CommentMapper.BaseResultMap")
    Comment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    @Update({
        "update comment",
        "set parent = #{parent,jdbcType=BIGINT},",
          "aid = #{aid,jdbcType=VARCHAR},",
          "publish_time = #{publishTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Comment record);
}