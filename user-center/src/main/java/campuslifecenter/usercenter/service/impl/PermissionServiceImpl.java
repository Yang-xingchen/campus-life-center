package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.RolePermissionExample;
import campuslifecenter.usercenter.entry.RolePermissionKey;
import campuslifecenter.usercenter.mapper.PermissionMapper;
import campuslifecenter.usercenter.mapper.RolePermissionMapper;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    @NewSpan("permission")
    public List<Permission> getPermission(int oid, int rid) {
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andOidEqualTo(oid).andRidEqualTo(rid);
        return rolePermissionMapper.selectByExample(rolePermissionExample)
                .stream()
                .map(RolePermissionKey::getPid)
                .map(permissionMapper::selectByPrimaryKey)
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("authentication")
    public boolean authentication(AccountInfo accountInfo, int oid, int type, String permissionName) {
        return accountInfo
                .getOrganizations()
                .stream()
                .filter(organization -> organization.getId() == oid)
                .findAny()
                .map(OrganizationInfo::getRoles)
                .map(roleInfos -> roleInfos
                        .stream()
                        .flatMap(roleInfo -> roleInfo.getPermissions().stream())
                        .anyMatch(permission -> permission.getType() == type && permissionName.equals(permission.getName())))
                .orElse(false);
    }

    @Override
    @NewSpan("add role permission")
    public boolean addRolePermission(@SpanTag("organization") int oid,
                                     @SpanTag("role") int rid,
                                     @SpanTag("permission") int pid) {
        RolePermissionKey permission = new RolePermissionKey();
        permission.withOid(oid).withRid(1).withPid(pid);
        rolePermissionMapper.insert(permission);
        return true;
    }

}
