package campuslifecenter.info.mapper;

import campuslifecenter.info.entry.OrganizationSaveInfo;
import campuslifecenter.info.entry.OrganizationSaveInfoExample;
import campuslifecenter.info.entry.OrganizationSaveInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface OrganizationSaveInfoMapper {
    long countByExample(OrganizationSaveInfoExample example);

    int deleteByExample(OrganizationSaveInfoExample example);

    @Delete({
        "delete from organization_save_info",
        "where oid = #{oid,jdbcType=INTEGER}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(OrganizationSaveInfoKey key);

    @Insert({
        "insert into organization_save_info (oid, id, ",
        "multiple_index, content)",
        "values (#{oid,jdbcType=INTEGER}, #{id,jdbcType=BIGINT}, ",
        "#{multipleIndex,jdbcType=INTEGER}, #{content,jdbcType=VARCHAR})"
    })
    int insert(OrganizationSaveInfo record);

    int insertSelective(OrganizationSaveInfo record);

    List<OrganizationSaveInfo> selectByExampleWithRowbounds(OrganizationSaveInfoExample example, RowBounds rowBounds);

    List<OrganizationSaveInfo> selectByExample(OrganizationSaveInfoExample example);

    @Select({
        "select",
        "oid, id, multiple_index, content",
        "from organization_save_info",
        "where oid = #{oid,jdbcType=INTEGER}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    @ResultMap("campuslifecenter.info.mapper.OrganizationSaveInfoMapper.BaseResultMap")
    OrganizationSaveInfo selectByPrimaryKey(OrganizationSaveInfoKey key);

    int updateByExampleSelective(@Param("record") OrganizationSaveInfo record, @Param("example") OrganizationSaveInfoExample example);

    int updateByExample(@Param("record") OrganizationSaveInfo record, @Param("example") OrganizationSaveInfoExample example);

    int updateByPrimaryKeySelective(OrganizationSaveInfo record);

    @Update({
        "update organization_save_info",
        "set content = #{content,jdbcType=VARCHAR}",
        "where oid = #{oid,jdbcType=INTEGER}",
          "and id = #{id,jdbcType=BIGINT}",
          "and multiple_index = #{multipleIndex,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrganizationSaveInfo record);
}