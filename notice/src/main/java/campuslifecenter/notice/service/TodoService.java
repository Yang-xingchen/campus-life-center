package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountTodoInfo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.model.TodoInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
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

    @PostMapping("/NoticesTodo")
    Response<List<TodoInfo>> getTodoBySources(@RequestBody List<String> sources);

    class AddTodoRequest implements Serializable {

        private List<String> values;
        private List<String> aids;

        public List<String> getValues() {
            return values;
        }

        public AddTodoRequest setValues(List<String> values) {
            this.values = values;
            return this;
        }

        public List<String> getAids() {
            return aids;
        }

        public AddTodoRequest setAids(List<String> aids) {
            this.aids = aids;
            return this;
        }
    }
}
