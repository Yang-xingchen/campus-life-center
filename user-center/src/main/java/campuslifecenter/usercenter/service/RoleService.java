package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.model.AddRoleRequest;

public interface RoleService {

    int add(AddRoleRequest role);

    boolean remove(int oid, int rid, String aid);
}
