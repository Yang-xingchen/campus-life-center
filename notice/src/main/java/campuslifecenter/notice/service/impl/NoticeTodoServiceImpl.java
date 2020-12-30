package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.AccountNoticeTodoMapper;
import campuslifecenter.notice.mapper.NoticeTodoMapper;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.service.NoticeTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeTodoServiceImpl implements NoticeTodoService {

    @Autowired
    private NoticeTodoMapper noticeTodoMapper;
    @Autowired
    private AccountNoticeTodoMapper accountNoticeTodoMapper;

    @Override
    public List<NoticeTodo> getTodoByNoticeId(long id) {
        NoticeTodoExample example = new NoticeTodoExample();
        example.createCriteria().andNidEqualTo(id);
        return noticeTodoMapper.selectByExample(example);
    }

    @Override
    public List<String> getTodoAccountIdByNoticeId(NoticeTodoKey key, boolean finish) {
        AccountNoticeTodoExample example = new AccountNoticeTodoExample();
        example.createCriteria()
                .andNidEqualTo(key.getNid())
                .andIdEqualTo(key.getId())
                .andFinishEqualTo(finish);
        return accountNoticeTodoMapper
                .selectByExample(example)
                .stream()
                .map(AccountNoticeTodoKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountNoticeInfo.AccountTodo> getTodoByAccount(String aid) {
        AccountNoticeTodoExample example = new AccountNoticeTodoExample();
        example.createCriteria().andAidEqualTo(aid);
        return accountNoticeTodoMapper.selectByExample(example)
                .stream()
                .map(todo -> {
                    NoticeTodoKey noticeTodoKey = new NoticeTodoKey()
                            .withId(todo.getId())
                            .withNid(todo.getNid());
                    return new AccountNoticeInfo.AccountTodo()
                            .setAccountNoticeTodo(todo)
                            .setNoticeTodo(noticeTodoMapper.selectByPrimaryKey(noticeTodoKey));
                })
                .collect(Collectors.toList());
    }



}
