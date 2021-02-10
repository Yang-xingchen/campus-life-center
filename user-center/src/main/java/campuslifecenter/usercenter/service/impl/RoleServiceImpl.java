package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.entry.Role;
import campuslifecenter.usercenter.entry.RoleExample;
import campuslifecenter.usercenter.entry.RoleKey;
import campuslifecenter.usercenter.mapper.RoleMapper;
import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.service.PermissionService;
import campuslifecenter.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TracerUtil tracerUtil;

    @Override
    @NewSpan("add role")
    public int add(AddRoleRequest role) {
        int oid = role.getOid();
        RoleExample example = new RoleExample();
        example.createCriteria().andOidEqualTo(oid);
        int id;
        if (role.getId() != null) {
            id = role.getId();
            tracerUtil.getSpan().tag("exist", "true");
        } else {
            id = roleMapper
                    .selectByExample(example)
                    .stream()
                    .mapToInt(RoleKey::getId)
                    .max()
                    .orElse(0) + 1;
            tracerUtil.getSpan().tag("exist", "false");
        }
        role.getAids().forEach(aid -> {
            Role role1 = new Role();
            role1.withName(role.getName()).withAid(aid).withOid(oid).withId(id);
            roleMapper.insert(role1);
        });
        tracerUtil.getSpan().tag("organization", oid + "");
        role.getPermissions().forEach(permission -> {
            permissionService.addRolePermission(oid, id, permission.getId());
        });
        return id;
    }

    @Override
    public boolean remove(int oid, int rid, String aid) {
        Role role = new Role();
        role.withId(rid).withOid(oid).withAid(aid);
        return roleMapper.deleteByPrimaryKey(role) == 1;
    }


}
