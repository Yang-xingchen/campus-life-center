package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.DynamicInfoObserve;
import campuslifecenter.notice.entry.DynamicInfoObserveExample;
import campuslifecenter.notice.entry.DynamicInfoObserveKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface DynamicInfoObserveMapper {
    long countByExample(DynamicInfoObserveExample example);

    int deleteByExample(DynamicInfoObserveExample example);

    @Delete({
        "delete from dynamic_info_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(DynamicInfoObserveKey key);

    @Insert({
        "insert into dynamic_info_observe (nid, tid, ",
        "iid, type, value)",
        "values (#{nid,jdbcType=BIGINT}, #{tid,jdbcType=INTEGER}, ",
        "#{iid,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, #{value,jdbcType=VARCHAR})"
    })
    int insert(DynamicInfoObserve record);

    int insertSelective(DynamicInfoObserve record);

    List<DynamicInfoObserve> selectByExampleWithRowbounds(DynamicInfoObserveExample example, RowBounds rowBounds);

    List<DynamicInfoObserve> selectByExample(DynamicInfoObserveExample example);

    @Select({
        "select",
        "nid, tid, iid, type, value",
        "from dynamic_info_observe",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    @ResultMap("campuslifecenter.notice.mapper.DynamicInfoObserveMapper.BaseResultMap")
    DynamicInfoObserve selectByPrimaryKey(DynamicInfoObserveKey key);

    int updateByExampleSelective(@Param("record") DynamicInfoObserve record, @Param("example") DynamicInfoObserveExample example);

    int updateByExample(@Param("record") DynamicInfoObserve record, @Param("example") DynamicInfoObserveExample example);

    int updateByPrimaryKeySelective(DynamicInfoObserve record);

    @Update({
        "update dynamic_info_observe",
        "set type = #{type,jdbcType=INTEGER},",
          "value = #{value,jdbcType=VARCHAR}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and tid = #{tid,jdbcType=INTEGER}",
          "and iid = #{iid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DynamicInfoObserve record);
}