package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.SecurityLogExample;
import campuslifecenter.usercenter.entry.SecurityLogKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SecurityLogMapper {
    long countByExample(SecurityLogExample example);

    int deleteByExample(SecurityLogExample example);

    @Delete({
        "delete from security_log",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and input_time = #{inputTime,jdbcType=TIMESTAMP}"
    })
    int deleteByPrimaryKey(SecurityLogKey key);

    @Insert({
        "insert into security_log (aid, input_time)",
        "values (#{aid,jdbcType=VARCHAR}, #{inputTime,jdbcType=TIMESTAMP})"
    })
    int insert(SecurityLogKey record);

    int insertSelective(SecurityLogKey record);

    List<SecurityLogKey> selectByExampleWithRowbounds(SecurityLogExample example, RowBounds rowBounds);

    List<SecurityLogKey> selectByExample(SecurityLogExample example);

    int updateByExampleSelective(@Param("record") SecurityLogKey record, @Param("example") SecurityLogExample example);

    int updateByExample(@Param("record") SecurityLogKey record, @Param("example") SecurityLogExample example);
}