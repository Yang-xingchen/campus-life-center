package campuslifecenter.notice.controller;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.NoticeTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Api("代办事项")
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

    @ApiOperation("通过token获取待办列表")
    @GetMapping("/todoList")
    public Response<List<AccountNoticeInfo.AccountTodo>> getAccountByToken(
            @ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> todoService.getTodoByAccount(getAccountIdByToken(token)));
    }

    @ApiOperation("更新待办信息")
    @PostMapping("/update")
    public Response<Boolean> update(@ApiParam("待办信息") @RequestBody AccountNoticeTodo accountTodo,
                                    @RequestParam String token) {
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
