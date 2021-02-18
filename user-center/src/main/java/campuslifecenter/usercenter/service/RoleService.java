package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.model.RoleInfo;
import campuslifecenter.usercenter.model.UpdateRoleRequest;

import java.util.List;

public interface RoleService {

    List<RoleInfo> getRole(String aid, int oid);

    int add(AddRoleRequest role);

    boolean update(UpdateRoleRequest role);
}
