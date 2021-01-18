package campuslifecenter.todo.controller;

import brave.Tracer;
import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.Todo;
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
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取用户所有待办")
    @GetMapping("/AccountAllTodo")
    public Response<List<AccountTodoInfo>> getTodoByToken(@RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        return Response.withData(() -> todoService
                .getTodoByAccount(aid)
                .stream()
                .map(todo -> new AccountTodoInfo().setAccountNoticeTodo(todo))
                .peek(todoInfo -> todoInfo.setTodo(todoService.getTodoById(todoInfo.getId())))
                .collect(Collectors.toList())
        );
    }

    @ApiOperation("获取来源下所有用户待办")
    @GetMapping("/NoticeAllTodo")
    public Response<List<AccountTodoInfo>> getTodoBySource(@RequestParam String source) {
        tracer.currentSpan().tag("source", source);
        return Response.withData(() -> todoService
                .getTodoBySource(source)
                .stream()
                .peek(todoInfo -> todoInfo.setAccountName(cacheService.getAccountNameByID(todoInfo.getAid())))
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/NoticesTodo")
    public Response<List<Todo>> getTodoBySources(@RequestBody List<String> sources) {
        return Response.withData(() -> todoService.getTodoBySources(sources));
    }

    @ApiOperation("获取来源下某一用户待办")
    @GetMapping("/todo")
    public Response<List<AccountTodoInfo>> getTodoByTokenAndSource(@RequestParam String token, @RequestParam String source) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("source", source);
        return Response.withData(() -> todoService
                .getTodoByAccountAndSource(aid, source)
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
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("tid", accountTodo.getId() + "");
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
