package campuslifecenter.notice.model;

import java.io.Serializable;
import java.util.Objects;

public class IdName<ID> implements Serializable {
    private ID id;
    private String name;

    public IdName() {
    }

    public IdName(ID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ID getId() {
        return id;
    }

    public IdName<ID> setId(ID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public IdName<ID> setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdName<?> idName = (IdName<?>) o;
        return Objects.equals(id, idName.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
