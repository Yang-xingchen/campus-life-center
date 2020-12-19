package campuslifecenter.usercenter.mapper;

import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.entry.SignInLogExample;
import campuslifecenter.usercenter.entry.SignInLogKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface SignInLogMapper {
    long countByExample(SignInLogExample example);

    int deleteByExample(SignInLogExample example);

    @Delete({
        "delete from signinlog",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and sign_in_time = #{signInTime,jdbcType=TIMESTAMP}"
    })
    int deleteByPrimaryKey(SignInLogKey key);

    @Insert({
        "insert into signinlog (aid, sign_in_time, ",
        "sign_in_id, sign_out_time, ",
        "ip, source, type, ",
        "token)",
        "values (#{aid,jdbcType=VARCHAR}, #{signInTime,jdbcType=TIMESTAMP}, ",
        "#{signInId,jdbcType=VARCHAR}, #{signOutTime,jdbcType=TIMESTAMP}, ",
        "#{ip,jdbcType=VARCHAR}, #{source,jdbcType=TINYINT}, #{type,jdbcType=TINYINT}, ",
        "#{token,jdbcType=VARCHAR})"
    })
    int insert(SignInLog record);

    int insertSelective(SignInLog record);

    List<SignInLog> selectByExampleWithRowbounds(SignInLogExample example, RowBounds rowBounds);

    List<SignInLog> selectByExample(SignInLogExample example);

    @Select({
        "select",
        "aid, sign_in_time, sign_in_id, sign_out_time, ip, source, type, token",
        "from signinlog",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and sign_in_time = #{signInTime,jdbcType=TIMESTAMP}"
    })
    @ResultMap("campuslifecenter.usercenter.mapper.SignInLogMapper.BaseResultMap")
    SignInLog selectByPrimaryKey(SignInLogKey key);

    int updateByExampleSelective(@Param("record") SignInLog record, @Param("example") SignInLogExample example);

    int updateByExample(@Param("record") SignInLog record, @Param("example") SignInLogExample example);

    int updateByPrimaryKeySelective(SignInLog record);

    @Update({
        "update signinlog",
        "set sign_in_id = #{signInId,jdbcType=VARCHAR},",
          "sign_out_time = #{signOutTime,jdbcType=TIMESTAMP},",
          "ip = #{ip,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=TINYINT},",
          "type = #{type,jdbcType=TINYINT},",
          "token = #{token,jdbcType=VARCHAR}",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and sign_in_time = #{signInTime,jdbcType=TIMESTAMP}"
    })
    int updateByPrimaryKey(SignInLog record);
}