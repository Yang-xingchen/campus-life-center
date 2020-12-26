package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.AccountNoticeExample;
import campuslifecenter.notice.entry.AccountNoticeKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountNoticeMapper {
    long countByExample(AccountNoticeExample example);

    int deleteByExample(AccountNoticeExample example);

    @Delete({
        "delete from account_notice",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(AccountNoticeKey key);

    @Insert({
        "insert into account_notice (nid, aid, ",
        "is_read, is_top, is_delete, ",
        "relative_importance)",
        "values (#{nid,jdbcType=BIGINT}, #{aid,jdbcType=VARCHAR}, ",
        "#{isRead,jdbcType=BIT}, #{isTop,jdbcType=BIT}, #{isDelete,jdbcType=BIT}, ",
        "#{relativeImportance,jdbcType=INTEGER})"
    })
    int insert(AccountNotice record);

    int insertSelective(AccountNotice record);

    List<AccountNotice> selectByExampleWithRowbounds(AccountNoticeExample example, RowBounds rowBounds);

    List<AccountNotice> selectByExample(AccountNoticeExample example);

    @Select({
        "select",
        "nid, aid, is_read, is_top, is_delete, relative_importance",
        "from account_notice",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.notice.mapper.AccountNoticeMapper.BaseResultMap")
    AccountNotice selectByPrimaryKey(AccountNoticeKey key);

    int updateByExampleSelective(@Param("record") AccountNotice record, @Param("example") AccountNoticeExample example);

    int updateByExample(@Param("record") AccountNotice record, @Param("example") AccountNoticeExample example);

    int updateByPrimaryKeySelective(AccountNotice record);

    @Update({
        "update account_notice",
        "set is_read = #{isRead,jdbcType=BIT},",
          "is_top = #{isTop,jdbcType=BIT},",
          "is_delete = #{isDelete,jdbcType=BIT},",
          "relative_importance = #{relativeImportance,jdbcType=INTEGER}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(AccountNotice record);
}