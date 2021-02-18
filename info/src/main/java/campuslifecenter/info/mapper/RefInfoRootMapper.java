package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.RefInfoRoot;
import campuslifecenter.info.entry.RefInfoRootExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface RefInfoRootMapper {
    long countByExample(RefInfoRootExample example);

    int deleteByExample(RefInfoRootExample example);

    @Delete({
        "delete from ref_info_root",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String ref);

    @Insert({
        "insert into ref_info_root (ref, root)",
        "values (#{ref,jdbcType=VARCHAR}, #{root,jdbcType=BIGINT})"
    })
    int insert(RefInfoRoot record);

    int insertSelective(RefInfoRoot record);

    List<RefInfoRoot> selectByExampleWithRowbounds(RefInfoRootExample example, RowBounds rowBounds);

    List<RefInfoRoot> selectByExample(RefInfoRootExample example);

    @Select({
        "select",
        "ref, root",
        "from ref_info_root",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.info.mapper.RefInfoRootMapper.BaseResultMap")
    RefInfoRoot selectByPrimaryKey(String ref);

    int updateByExampleSelective(@Param("record") RefInfoRoot record, @Param("example") RefInfoRootExample example);

    int updateByExample(@Param("record") RefInfoRoot record, @Param("example") RefInfoRootExample example);

    int updateByPrimaryKeySelective(RefInfoRoot record);

    @Update({
        "update ref_info_root",
        "set root = #{root,jdbcType=BIGINT}",
        "where ref = #{ref,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(RefInfoRoot record);
}