package campuslifecenter.todo.model;

import java.io.Serializable;
import java.util.List;

public class AddTodoRequest implements Serializable {

    private String ref;
    private int type;
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
