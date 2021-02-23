package campuslifecenter.todo.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.Todo;
import campuslifecenter.todo.model.AccountTodoInfo;
import campuslifecenter.todo.model.AddTodoRequest;
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
@RestWarpController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    @ApiOperation("获取用户所有待办")
    @GetMapping("/account/all")
    public List<AccountTodoInfo> getTodoByToken(@RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        return todoService
                .getTodoByAccount(aid)
                .stream()
                .map(todo -> new AccountTodoInfo().setAccountNoticeTodo(todo))
                .peek(todoInfo -> todoInfo.setTodo(todoService.getTodoById(todoInfo.getId())))
                .collect(Collectors.toList());
    }

    @ApiOperation("获取来源下所有用户待办")
    @GetMapping("/ref/accounts")
    public List<AccountTodoInfo> getTodoBySource(@RequestParam String ref) {
        tracerUtil.getSpan().tag("ref", ref);
        return todoService
                .getTodoByRef(ref)
                .stream()
                .peek(todoInfo -> todoInfo.setAccountName(cacheService.getAccountNameByID(todoInfo.getAid())))
                .collect(Collectors.toList());
    }

    @GetMapping("/ref/todos")
    public List<Todo> getTodoListByRef(@RequestParam String ref) {
        return todoService.getTodoListByRef(ref);
    }

    @PostMapping("/refs/todos")
    public List<Todo> getTodoByRefs(@RequestBody List<String> refs) {
        return todoService.getTodoByRefs(refs);
    }

    @ApiOperation("获取来源下某一用户待办")
    @GetMapping("/todo")
    public List<AccountTodoInfo> getTodoByTokenAndRef(@RequestParam String token, @RequestParam String ref) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        tracerUtil.getSpan().tag("ref", ref);
        return todoService.getTodoByAccountAndRef(aid, ref);
    }

    @ApiOperation("添加待办")
    @PostMapping("/add")
    public boolean add(@RequestBody AddTodoRequest request) {
        return todoService.add(request);
    }

    @ApiOperation("查询")
    @GetMapping("/select/account")
    public List<String> select(@RequestParam long id, @RequestParam boolean finish) {
        return todoService.select(id, finish);
    }

    @ApiOperation("更新待办信息")
    @PostMapping("/account/update")
    public Boolean update(@ApiParam("待办信息") @RequestBody AccountTodo accountTodo,
                                    @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        tracerUtil.getSpan().tag("tid", accountTodo.getId() + "");
        Objects.requireNonNull(accountTodo.getId(), "id is null");
        AuthException.checkThrow(aid, accountTodo.getAid());
        return todoService.update(accountTodo);
    }

    @ApiOperation("更新接收待办成员")
    @PostMapping("/update/accounts")
    public Boolean updateAccount(@RequestBody List<String> aids, @RequestParam String ref) {
        tracerUtil.getSpan().tag("todo ref", ref);
        return todoService.updateAccount(ref, aids);
    }

}
