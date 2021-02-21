package campuslifecenter.common.model;

import java.io.Serializable;
import java.util.List;

public class ConditionAccountUpdate implements Serializable {

    private String aid;

    private List<String> refs;

    public String getAid() {
        return aid;
    }

    public ConditionAccountUpdate setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public List<String> getRefs() {
        return refs;
    }

    public ConditionAccountUpdate setRefs(List<String> refs) {
        this.refs = refs;
        return this;
    }
}
