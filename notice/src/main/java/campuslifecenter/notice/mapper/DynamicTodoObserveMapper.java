package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.DynamicTodoObserve;
import campuslifecenter.notice.entry.DynamicTodoObserveExample;
import campuslifecenter.notice.entry.DynamicTodoObserveKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface DynamicTodoObserveMapper {
    long countByExample(DynamicTodoObserveExample example);

    int deleteByExample(DynamicTodoObserveExample example);

    @Delete({
        "delete from dynamic_todo_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(DynamicTodoObserveKey key);

    @Insert({
        "insert into dynamic_todo_observe (nid, tid, ",
        "is_finish)",
        "values (#{nid,jdbcType=BIGINT}, #{tid,jdbcType=INTEGER}, ",
        "#{isFinish,jdbcType=BIT})"
    })
    int insert(DynamicTodoObserve record);

    int insertSelective(DynamicTodoObserve record);

    List<DynamicTodoObserve> selectByExampleWithRowbounds(DynamicTodoObserveExample example, RowBounds rowBounds);

    List<DynamicTodoObserve> selectByExample(DynamicTodoObserveExample example);

    @Select({
        "select",
        "nid, tid, is_finish",
        "from dynamic_todo_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.notice.mapper.DynamicTodoObserveMapper.BaseResultMap")
    DynamicTodoObserve selectByPrimaryKey(DynamicTodoObserveKey key);

    int updateByExampleSelective(@Param("record") DynamicTodoObserve record, @Param("example") DynamicTodoObserveExample example);

    int updateByExample(@Param("record") DynamicTodoObserve record, @Param("example") DynamicTodoObserveExample example);

    int updateByPrimaryKeySelective(DynamicTodoObserve record);

    @Update({
        "update dynamic_todo_observe",
        "set is_finish = #{isFinish,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DynamicTodoObserve record);
}