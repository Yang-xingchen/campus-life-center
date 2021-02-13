package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.model.UpdateRoleRequest;

public interface RoleService {

    int add(AddRoleRequest role);

    boolean update(UpdateRoleRequest role);
}
