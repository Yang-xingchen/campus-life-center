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

    @ApiOperation("获取用户所有待办信息")
    @GetMapping("/todoList")
    public Response<List<AccountTodoInfo>> getTodoList(
            @ApiParam("token") @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam("来源") @RequestParam(required = false, defaultValue = "") String source) {
        if ("".equals(token) && "".equals(source)) {
            return new Response<List<AccountTodoInfo>>()
                    .setCode(400)
                    .setMessage("待办获取失败: token=" + token + ", source=" + source)
                    .setSuccess(false);
        }
        if ("".equals(token)) {
            return getTodoBySource(source);
        } else if ("".equals(source)) {
            return getTodoByToken(token);
        } else {
            return getTodoByTokenAndSource(token, source);
        }
    }

    private Response<List<AccountTodoInfo>> getTodoByToken(String token) {
        return Response.withData(() -> todoService
                .getTodoByAccount(cacheService.getAccountIdByToken(token))
                .stream()
                .map(todo -> new AccountTodoInfo().setAccountNoticeTodo(todo))
                .peek(todoInfo -> todoInfo.setTodo(todoService.getTodoById(todoInfo.getId())))
                .collect(Collectors.toList())
        );
    }

    private Response<List<AccountTodoInfo>> getTodoBySource(String source) {
        return Response.withData(() -> todoService
                .getTodoBySource(source)
                .stream()
                .peek(todoInfo -> todoInfo.setAccountName(cacheService.getAccountNameByID(todoInfo.getAid())))
                .collect(Collectors.toList())
        );
    }

    private Response<List<AccountTodoInfo>> getTodoByTokenAndSource(String token, String source) {
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
