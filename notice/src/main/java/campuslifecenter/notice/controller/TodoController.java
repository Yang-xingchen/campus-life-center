package campuslifecenter.notice.controller;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.NoticeTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private NoticeTodoService todoService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    public static final String TOKEN_PREFIX = "TOKEN_";

    private String getAccountIdByToken(String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountInfo> response = accountService.info(token);
                    if (!response.isSuccess()) {
                        throw new IllegalArgumentException("account not found:" + response.getMessage());
                    }
                    return response.getData().getSignId();
                });
    }

    @GetMapping("/todoByToken/{token}")
    public Response<List<AccountNoticeInfo.AccountTodo>> getAccountByToken(@PathVariable("token") String token) {

        return Response.withData(() -> todoService.getTodoByAccount(getAccountIdByToken(token)));
    }

    @PostMapping("/update/{token}")
    public Response<Boolean> update(@RequestBody AccountNoticeTodo accountTodo,
                                    @PathVariable("token") String token) {
        return Response.withData(() -> {
            Objects.requireNonNull(accountTodo.getAid(), "aid is null");
            Objects.requireNonNull(accountTodo.getNid(), "nid is null");
            Objects.requireNonNull(accountTodo.getId(), "id is null");
            if (!Objects.equals(getAccountIdByToken(token), accountTodo.getAid())) {
                throw new IllegalArgumentException("account auth fail: " +
                        "token:" + getAccountIdByToken(token) +
                        "todo:" + accountTodo.getAid());
            }
            return todoService.update(accountTodo);
        });
    }

}
