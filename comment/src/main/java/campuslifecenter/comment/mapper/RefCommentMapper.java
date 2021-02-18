package campuslifecenter.comment.mapper;

import campuslifecenter.comment.entry.RefComment;
import campuslifecenter.comment.entry.RefCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RefCommentMapper {
    long countByExample(RefCommentExample example);

    int deleteByExample(RefCommentExample example);

    @Delete({
        "delete from ref_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into ref_comment (id, ref)",
        "values (#{id,jdbcType=BIGINT}, #{ref,jdbcType=VARCHAR})"
    })
    int insert(RefComment record);

    int insertSelective(RefComment record);

    List<RefComment> selectByExampleWithRowbounds(RefCommentExample example, RowBounds rowBounds);

    List<RefComment> selectByExample(RefCommentExample example);

    @Select({
        "select",
        "id, ref",
        "from ref_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.comment.mapper.RefCommentMapper.BaseResultMap")
    RefComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RefComment record, @Param("example") RefCommentExample example);

    int updateByExample(@Param("record") RefComment record, @Param("example") RefCommentExample example);

    int updateByPrimaryKeySelective(RefComment record);

    @Update({
        "update ref_comment",
        "set ref = #{ref,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RefComment record);
}