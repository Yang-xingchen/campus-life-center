package campuslifecenter.usercenter.model;

import java.util.List;

public class AddRoleRequest extends RoleInfo{

    private List<String> aids;

    public List<String> getAids() {
        return aids;
    }

    public AddRoleRequest setAids(List<String> aids) {
        this.aids = aids;
        return this;
    }
}
