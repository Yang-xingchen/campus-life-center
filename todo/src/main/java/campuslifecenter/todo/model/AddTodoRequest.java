package campuslifecenter.todo.model;

import java.io.Serializable;
import java.util.List;

public class AddTodoRequest implements Serializable {

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
