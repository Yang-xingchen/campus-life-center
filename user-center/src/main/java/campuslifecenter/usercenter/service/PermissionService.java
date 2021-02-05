package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.Permission;

import java.util.List;

public interface PermissionService {

    List<Permission> getPermission(int oid, int rid);

}
