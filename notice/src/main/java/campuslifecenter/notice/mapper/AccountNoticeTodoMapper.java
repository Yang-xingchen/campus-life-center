package campuslifecenter.notice.mapper;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.entry.AccountNoticeTodoExample;
import campuslifecenter.notice.entry.AccountNoticeTodoKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

public interface AccountNoticeTodoMapper {
    long countByExample(AccountNoticeTodoExample example);

    int deleteByExample(AccountNoticeTodoExample example);

    @Delete({
        "delete from account_notice_todo",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=INTEGER}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(AccountNoticeTodoKey key);

    @Insert({
        "insert into account_notice_todo (nid, id, ",
        "aid, finish, top, ",
        "add_list)",
        "values (#{nid,jdbcType=BIGINT}, #{id,jdbcType=INTEGER}, ",
        "#{aid,jdbcType=VARCHAR}, #{finish,jdbcType=BIT}, #{top,jdbcType=BIT}, ",
        "#{addList,jdbcType=BIT})"
    })
    int insert(AccountNoticeTodo record);

    int insertSelective(AccountNoticeTodo record);

    List<AccountNoticeTodo> selectByExampleWithRowbounds(AccountNoticeTodoExample example, RowBounds rowBounds);

    List<AccountNoticeTodo> selectByExample(AccountNoticeTodoExample example);

    @Select({
        "select",
        "nid, id, aid, finish, top, add_list",
        "from account_notice_todo",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=INTEGER}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    @ResultMap("campuslifecenter.notice.mapper.AccountNoticeTodoMapper.BaseResultMap")
    AccountNoticeTodo selectByPrimaryKey(AccountNoticeTodoKey key);

    int updateByExampleSelective(@Param("record") AccountNoticeTodo record, @Param("example") AccountNoticeTodoExample example);

    int updateByExample(@Param("record") AccountNoticeTodo record, @Param("example") AccountNoticeTodoExample example);

    int updateByPrimaryKeySelective(AccountNoticeTodo record);

    @Update({
        "update account_notice_todo",
        "set finish = #{finish,jdbcType=BIT},",
          "top = #{top,jdbcType=BIT},",
          "add_list = #{addList,jdbcType=BIT}",
        "where nid = #{nid,jdbcType=BIGINT}",
          "and id = #{id,jdbcType=INTEGER}",
          "and aid = #{aid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(AccountNoticeTodo record);
}