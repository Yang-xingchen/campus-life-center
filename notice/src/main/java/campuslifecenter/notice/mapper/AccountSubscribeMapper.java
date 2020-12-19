package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.AccountSubscribeExample;
import campuslifecenter.notice.entry.AccountSubscribeKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AccountSubscribeMapper {
    long countByExample(AccountSubscribeExample example);

    int deleteByExample(AccountSubscribeExample example);

    @Delete({
        "delete from account_subscribe",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and oid = #{oid,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AccountSubscribeKey key);

    @Insert({
        "insert into account_subscribe (aid, oid)",
        "values (#{aid,jdbcType=VARCHAR}, #{oid,jdbcType=INTEGER})"
    })
    int insert(AccountSubscribeKey record);

    int insertSelective(AccountSubscribeKey record);

    List<AccountSubscribeKey> selectByExampleWithRowbounds(AccountSubscribeExample example, RowBounds rowBounds);

    List<AccountSubscribeKey> selectByExample(AccountSubscribeExample example);

    int updateByExampleSelective(@Param("record") AccountSubscribeKey record, @Param("example") AccountSubscribeExample example);

    int updateByExample(@Param("record") AccountSubscribeKey record, @Param("example") AccountSubscribeExample example);
}