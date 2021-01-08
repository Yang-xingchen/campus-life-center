package campuslifecenter.todo.controller;

import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.model.AccountTodoInfo;
import campuslifecenter.todo.model.AddTodoRequest;
import campuslifecenter.todo.model.Response;
import campuslifecenter.todo.service.CacheService;
import campuslifecenter.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api("代办事项")
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private CacheService cacheService;

    @ApiOperation("通过token获取账户的所有待办列表")
    @GetMapping("/todoList")
    public Response<List<AccountTodoInfo>> getTodoByToken(
            @ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> todoService
                .getTodoByAccount(cacheService.getAccountIdByToken(token))
                .stream()
                .map(todo -> new AccountTodoInfo().setAccountNoticeTodo(todo))
                .peek(todoInfo -> todoInfo.setTodo(todoService.getTodoById(todoInfo.getId())))
                .collect(Collectors.toList())
        );
    }

    @ApiOperation("通过来源获取通知的所有账户待办列表")
    @GetMapping("/todoList")
    public Response<List<AccountTodoInfo>> getTodoBySource(
            @ApiParam("来源") @RequestParam String source) {
        return Response.withData(() -> todoService
                .getTodoBySource(source)
                .stream()
                .peek(todoInfo -> todoInfo.setAccountName(cacheService.getAccountNameByID(todoInfo.getAid())))
                .collect(Collectors.toList())
        );
    }

    @ApiOperation("获取账户中某个来源下所有待办")
    @GetMapping("/todoList")
    public Response<List<AccountTodoInfo>> getTodoByTokenAndSource(
            @ApiParam("token") @RequestParam String token,
            @ApiParam("来源") @RequestParam String source) {
        return Response.withData(() -> todoService
                .getTodoByAccountAndSource(cacheService.getAccountIdByToken(token), source)
        );
    }

    @ApiOperation("添加待办")
    @PostMapping("/add")
    public Response<String> add(@RequestBody AddTodoRequest request) {
        return Response.withData(() -> todoService.add(request));
    }

    @ApiOperation("查询")
    @GetMapping("/selectAccount")
    public Response<List<String>> select(@RequestParam long id, @RequestParam boolean finish) {
        return Response.withData(() -> todoService.select(id, finish));
    }

    @ApiOperation("更新待办信息")
    @PostMapping("/update")
    public Response<Boolean> update(@ApiParam("待办信息") @RequestBody AccountTodo accountTodo,
                                    @RequestParam String token) {
        return Response.withData(() -> {
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
