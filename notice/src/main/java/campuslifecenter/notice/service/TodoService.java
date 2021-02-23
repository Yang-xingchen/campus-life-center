package campuslifecenter.notice.service;

import campuslifecenter.common.model.Response;
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

    @GetMapping("/ref/todos")
    Response<List<Todo>> getTodoListByRef(@RequestParam String ref);

    @GetMapping("/todo")
    Response<List<AccountTodoInfo>> getTodoByTokenAndRef(
            @RequestParam String token, @RequestParam String ref);

    @PostMapping("/add")
    Response<Boolean> add(@RequestBody AddTodoRequest request);

    @PostMapping("/refs/todos")
    Response<List<Todo>> getTodoByRefs(@RequestBody List<String> refs);

    @PostMapping("/update/accounts")
    Response<Boolean> updateAccount(@RequestBody List<String> aids, @RequestParam String ref);

    class AddTodoRequest implements Serializable {

        private String ref;
        private int type = 1;
        private List<String> values;
        private List<String> aids;
        private String link;

        public String getRef() {
            return ref;
        }

        public AddTodoRequest setRef(String ref) {
            this.ref = ref;
            return this;
        }

        public int getType() {
            return type;
        }

        public AddTodoRequest setType(int type) {
            this.type = type;
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

        public String getLink() {
            return link;
        }

        public AddTodoRequest setLink(String link) {
            this.link = link;
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

    class TodoInfo implements Serializable {
        private Long id;
        private String value;
        private String ref;
        private Integer source;

        public Long getId() {
            return id;
        }

        public TodoInfo setId(Long id) {
            this.id = id;
            return this;
        }

        public String getValue() {
            return value;
        }

        public TodoInfo setValue(String value) {
            this.value = value;
            return this;
        }

        public String getRef() {
            return ref;
        }

        public TodoInfo setRef(String ref) {
            this.ref = ref;
            return this;
        }

        public int getSource() {
            return source;
        }

        public TodoInfo setSource(int source) {
            this.source = source;
            return this;
        }
    }

    class AccountTodoInfo extends TodoInfo {
        private String aid;
        private String accountName;
        private Boolean finish;
        private Boolean top;
        private Boolean addList;

        public String getAid() {
            return aid;
        }

        public AccountTodoInfo setAid(String aid) {
            this.aid = aid;
            return this;
        }

        public String getAccountName() {
            return accountName;
        }

        public AccountTodoInfo setAccountName(String accountName) {
            this.accountName = accountName;
            return this;
        }

        public Boolean getFinish() {
            return finish;
        }

        public AccountTodoInfo setFinish(Boolean finish) {
            this.finish = finish;
            return this;
        }

        public Boolean getTop() {
            return top;
        }

        public AccountTodoInfo setTop(Boolean top) {
            this.top = top;
            return this;
        }

        public Boolean getAddList() {
            return addList;
        }

        public AccountTodoInfo setAddList(Boolean addList) {
            this.addList = addList;
            return this;
        }
    }
}
