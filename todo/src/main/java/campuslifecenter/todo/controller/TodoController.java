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
    @GetMapping("/AccountAllTodo")
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
    @GetMapping("/NoticeAllTodo")
    public List<AccountTodoInfo> getTodoBySource(@RequestParam String source) {
        tracerUtil.getSpan().tag("source", source);
        return todoService
                .getTodoByRef(source)
                .stream()
                .peek(todoInfo -> todoInfo.setAccountName(cacheService.getAccountNameByID(todoInfo.getAid())))
                .collect(Collectors.toList());
    }

    @GetMapping("/NoticeTodo")
    public List<Todo> getTodoListByRef(@RequestParam String ref) {
        return todoService.getTodoListBySource(ref);
    }

    @PostMapping("/NoticesTodo")
    public List<Todo> getTodoBySources(@RequestBody List<String> sources) {
        return todoService.getTodoByRefs(sources);
    }

    @ApiOperation("获取来源下某一用户待办")
    @GetMapping("/todo")
    public List<AccountTodoInfo> getTodoByTokenAndSource(@RequestParam String token, @RequestParam String source) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("aid", aid);
        tracerUtil.getSpan().tag("source", source);
        return todoService.getTodoByAccountAndRef(aid, source);
    }

    @ApiOperation("添加待办")
    @PostMapping("/add")
    public boolean add(@RequestBody AddTodoRequest request) {
        return todoService.add(request);
    }

    @ApiOperation("查询")
    @GetMapping("/selectAccount")
    public List<String> select(@RequestParam long id, @RequestParam boolean finish) {
        return todoService.select(id, finish);
    }

    @ApiOperation("更新待办信息")
    @PostMapping("/update")
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
    @PostMapping("/updateAccounts")
    public Boolean updateAccount(@RequestBody List<String> aids, @RequestParam String ref) {
        tracerUtil.getSpan().tag("todo ref", ref);
        return todoService.updateAccount(ref, aids);
    }

}
