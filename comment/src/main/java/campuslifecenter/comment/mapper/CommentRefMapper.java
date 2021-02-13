package campuslifecenter.comment.mapper;

import campuslifecenter.comment.entry.CommentRef;
import campuslifecenter.comment.entry.CommentRefExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface CommentRefMapper {
    long countByExample(CommentRefExample example);

    int deleteByExample(CommentRefExample example);

    @Delete({
        "delete from comment_ref",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into comment_ref (id, ref)",
        "values (#{id,jdbcType=BIGINT}, #{ref,jdbcType=VARCHAR})"
    })
    int insert(CommentRef record);

    int insertSelective(CommentRef record);

    List<CommentRef> selectByExampleWithRowbounds(CommentRefExample example, RowBounds rowBounds);

    List<CommentRef> selectByExample(CommentRefExample example);

    @Select({
        "select",
        "id, ref",
        "from comment_ref",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.comment.mapper.CommentRefMapper.BaseResultMap")
    CommentRef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommentRef record, @Param("example") CommentRefExample example);

    int updateByExample(@Param("record") CommentRef record, @Param("example") CommentRefExample example);

    int updateByPrimaryKeySelective(CommentRef record);

    @Update({
        "update comment_ref",
        "set ref = #{ref,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CommentRef record);
}