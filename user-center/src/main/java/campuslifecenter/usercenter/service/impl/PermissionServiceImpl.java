package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.Permission;
import campuslifecenter.usercenter.entry.RolePermissionExample;
import campuslifecenter.usercenter.entry.RolePermissionKey;
import campuslifecenter.usercenter.mapper.PermissionMapper;
import campuslifecenter.usercenter.mapper.RolePermissionMapper;
import campuslifecenter.usercenter.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
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
}
