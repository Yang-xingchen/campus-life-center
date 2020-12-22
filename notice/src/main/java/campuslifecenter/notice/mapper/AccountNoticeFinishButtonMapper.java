package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.AccountNoticeFinishButtonExample;
import campuslifecenter.notice.entry.AccountNoticeFinishButtonKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface AccountNoticeFinishButtonMapper {
    long countByExample(AccountNoticeFinishButtonExample example);

    int deleteByExample(AccountNoticeFinishButtonExample example);

    @Delete({
        "delete from account_notice_finish_button",
        "where aid = #{aid,jdbcType=VARCHAR}",
          "and nbid = #{nbid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(AccountNoticeFinishButtonKey key);

    @Insert({
        "insert into account_notice_finish_button (aid, nbid)",
        "values (#{aid,jdbcType=VARCHAR}, #{nbid,jdbcType=BIGINT})"
    })
    int insert(AccountNoticeFinishButtonKey record);

    int insertSelective(AccountNoticeFinishButtonKey record);

    List<AccountNoticeFinishButtonKey> selectByExampleWithRowbounds(AccountNoticeFinishButtonExample example, RowBounds rowBounds);

    List<AccountNoticeFinishButtonKey> selectByExample(AccountNoticeFinishButtonExample example);

    int updateByExampleSelective(@Param("record") AccountNoticeFinishButtonKey record, @Param("example") AccountNoticeFinishButtonExample example);

    int updateByExample(@Param("record") AccountNoticeFinishButtonKey record, @Param("example") AccountNoticeFinishButtonExample example);
}