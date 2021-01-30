package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.RefRoot;
import campuslifecenter.info.entry.RefRootExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RefRootMapper {
    long countByExample(RefRootExample example);

    int deleteByExample(RefRootExample example);

    @Delete({
        "delete from ref_root",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into ref_root (ref, root)",
        "values (#{ref,jdbcType=VARCHAR}, #{root,jdbcType=BIGINT})"
    })
    int insert(RefRoot record);

    int insertSelective(RefRoot record);

    List<RefRoot> selectByExampleWithRowbounds(RefRootExample example, RowBounds rowBounds);

    List<RefRoot> selectByExample(RefRootExample example);

    @Select({
        "select",
        "ref, root",
        "from ref_root",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.info.mapper.RefRootMapper.BaseResultMap")
    RefRoot selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") RefRoot record, @Param("example") RefRootExample example);

    int updateByExample(@Param("record") RefRoot record, @Param("example") RefRootExample example);

    int updateByPrimaryKeySelective(RefRoot record);

    @Update({
        "update ref_root",
        "set root = #{root,jdbcType=BIGINT}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(RefRoot record);
}