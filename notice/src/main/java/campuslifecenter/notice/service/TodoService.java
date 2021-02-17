package campuslifecenter.notice.service;

import campuslifecenter.common.model.Response;
import campuslifecenter.notice.model.AccountTodoInfo;
import campuslifecenter.notice.model.TodoInfo;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;

@FeignClient(name = "todo", path = "/todo", contextId = "todo")
public interface TodoService {

    @GetMapping("/NoticeTodo")
    Response<List<Todo>> getTodoListByRef(@RequestParam String ref);

    @GetMapping("/NoticeAllTodo")
    Response<List<AccountTodoInfo>> getTodoBySource(@RequestParam String source);

    @GetMapping("/todo")
    Response<List<AccountTodoInfo>> getTodoByTokenAndSource(
            @RequestParam String token, @RequestParam String source);

    @PostMapping("/add")
    Response<Boolean> add(@RequestBody AddTodoRequest request);

    @GetMapping("/selectAccount")
    Response<List<String>> select(@RequestParam long id, @RequestParam boolean finish);

    @PostMapping("/NoticesTodo")
    Response<List<Todo>> getTodoBySources(@RequestBody List<String> sources);

    @PostMapping("/updateAccounts")
    Response<Boolean> updateAccount(@RequestBody List<String> aids, @RequestParam String ref);

    class AddTodoRequest implements Serializable {

        private String ref;
        private List<String> values;
        private List<String> aids;

        public String getRef() {
            return ref;
        }

        public AddTodoRequest setRef(String ref) {
            this.ref = ref;
            return this;
        }

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
    class Todo implements Serializable {

        @ApiModelProperty(value = "todo id")
        private Long id;

        @ApiModelProperty(value = "来源")
        private String ref;

        @ApiModelProperty(value = "内容")
        private String content;

        private static final long serialVersionUID = 1L;

        public Long getId() {
            return id;
        }

        public Todo withId(Long id) {
            this.setId(id);
            return this;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRef() {
            return ref;
        }

        public Todo withRef(String ref) {
            this.setRef(ref);
            return this;
        }

        public void setRef(String ref) {
            this.ref = ref == null ? null : ref.trim();
        }

        public String getContent() {
            return content;
        }

        public Todo withContent(String content) {
            this.setContent(content);
            return this;
        }

        public void setContent(String content) {
            this.content = content == null ? null : content.trim();
        }

    }
}
