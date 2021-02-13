package campuslifecenter.usercenter.model;

import java.io.Serializable;
import java.util.List;

public class UpdateRoleRequest implements Serializable {

    private int oid;
    private int id;
    private String name;
    private List<Integer> addPids;
    private List<Integer> delPids;
    private List<String> addAids;
    private List<String> delAids;

    public int getOid() {
        return oid;
    }

    public UpdateRoleRequest setOid(int oid) {
        this.oid = oid;
        return this;
    }

    public int getId() {
        return id;
    }

    public UpdateRoleRequest setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateRoleRequest setName(String name) {
        this.name = name;
        return this;
    }

    public List<Integer> getAddPids() {
        return addPids;
    }

    public UpdateRoleRequest setAddPids(List<Integer> addPids) {
        this.addPids = addPids;
        return this;
    }

    public List<Integer> getDelPids() {
        return delPids;
    }

    public UpdateRoleRequest setDelPids(List<Integer> delPids) {
        this.delPids = delPids;
        return this;
    }

    public List<String> getAddAids() {
        return addAids;
    }

    public UpdateRoleRequest setAddAids(List<String> addAids) {
        this.addAids = addAids;
        return this;
    }

    public List<String> getDelAids() {
        return delAids;
    }

    public UpdateRoleRequest setDelAids(List<String> delAids) {
        this.delAids = delAids;
        return this;
    }
}
