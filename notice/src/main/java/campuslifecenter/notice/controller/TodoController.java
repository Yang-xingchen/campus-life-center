package campuslifecenter.notice.controller;

import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.model.AccountTodo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.NoticeTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api("代办事项")
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private NoticeTodoService todoService;
    @Autowired
    private CacheService cacheService;

    @ApiOperation("通过token获取待办列表")
    @GetMapping("/todoList")
    public Response<List<AccountTodo>> getAccountByToken(
            @ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> todoService.getTodoByAccount(cacheService.getAccountIdByToken(token)));
    }

    @ApiOperation("更新待办信息")
    @PostMapping("/update")
    public Response<Boolean> update(@ApiParam("待办信息") @RequestBody AccountNoticeTodo accountTodo,
                                    @RequestParam String token) {
        return Response.withData(() -> {
            Objects.requireNonNull(accountTodo.getAid(), "aid is null");
            Objects.requireNonNull(accountTodo.getNid(), "nid is null");
            Objects.requireNonNull(accountTodo.getId(), "id is null");
            if (!Objects.equals(cacheService.getAccountIdByToken(token), accountTodo.getAid())) {
                throw new IllegalArgumentException("account auth fail: " +
                        "token:" + cacheService.getAccountIdByToken(token) +
                        "todo:" + accountTodo.getAid());
            }
            return todoService.update(accountTodo);
        });
    }

}
