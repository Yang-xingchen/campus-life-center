package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.RolePermissionExample;
import campuslifecenter.usercenter.entry.RolePermissionKey;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
import campuslifecenter.usercenter.mapper.PermissionMapper;
import campuslifecenter.usercenter.mapper.RolePermissionMapper;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static campuslifecenter.usercenter.model.PermissionConst.SYSTEM_LIST;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private OrganizationMapper organizationMapper;


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
    public boolean authentication(AccountInfo accountInfo, int oid, String permissionName) {
        if (!SYSTEM_LIST.contains(permissionName)) {
            if (Objects.equals(accountInfo.getId(), organizationMapper.selectByPrimaryKey(oid).getCreator())) {
                return true;
            }
        }
        return accountInfo
                .getOrganizations()
                .stream()
                .filter(organization -> organization.getId() == oid)
                .findAny()
                .map(OrganizationInfo::getRoles)
                .map(roleInfos -> roleInfos
                        .stream()
                        .flatMap(roleInfo -> roleInfo.getPermissions().stream())
                        .anyMatch(permission -> permissionName.equals(permission.getName())))
                .orElse(false);
    }

    @Override
    @NewSpan("add role permission")
    public boolean addRolePermission(@SpanTag("organization") int oid,
                                     @SpanTag("role") int rid,
                                     @SpanTag("permission") int pid) {
        RolePermissionKey permission = new RolePermissionKey();
        permission.withOid(oid).withRid(rid).withPid(pid);
        rolePermissionMapper.insert(permission);
        return true;
    }

    @Override
    @NewSpan("del role permission")
    public boolean delRolePermission(@SpanTag("organization") int oid,
                                     @SpanTag("role") int rid,
                                     @SpanTag("permission") int pid) {
        RolePermissionKey permission = new RolePermissionKey();
        permission.withOid(oid).withRid(rid).withPid(pid);
        rolePermissionMapper.deleteByPrimaryKey(permission);
        return true;
    }

    @Override
    @NewSpan("del role permission")
    public boolean delRole(@SpanTag("organization") int oid,
                           @SpanTag("role") int rid) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andOidEqualTo(oid).andRidEqualTo(rid);
        rolePermissionMapper.deleteByExample(example);
        return true;
    }

}
