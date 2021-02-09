package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.model.AccountInfo;
import org.springframework.cloud.sleuth.annotation.NewSpan;

import java.util.List;

public interface PermissionService {

    List<Permission> getPermission(int oid, int rid);

    boolean authentication(AccountInfo accountInfo, int oid, String permission);

    boolean addRolePermission(int oid, int rid, int pid);
}
