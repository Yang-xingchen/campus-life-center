package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountTodoInfo;
import campuslifecenter.notice.model.AddTodoRequest;
import campuslifecenter.notice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "todo", path = "/todo", contextId = "todo")
public interface TodoService {

    @GetMapping("/NoticeAllTodo")
    Response<List<AccountTodoInfo>> getTodoBySource(@RequestParam String source);

    @GetMapping("/todo")
    Response<List<AccountTodoInfo>> getTodoByTokenAndSource(
            @RequestParam String token, @RequestParam String source);

    @GetMapping("/add")
    Response<String> add(@RequestBody AddTodoRequest request);

    @GetMapping("/selectAccount")
    Response<List<String>> select(@RequestParam long id, @RequestParam boolean finish);
}
