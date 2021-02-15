package campuslifecenter.usercenter.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.entry.Role;
import campuslifecenter.usercenter.entry.RoleExample;
import campuslifecenter.usercenter.entry.RoleKey;
import campuslifecenter.usercenter.mapper.RoleMapper;
import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.model.UpdateRoleRequest;
import campuslifecenter.usercenter.service.PermissionService;
import campuslifecenter.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TracerUtil tracerUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${user-center.cache.account-info}")
    private String ACCOUNT_INFO;

    @Override
    @NewSpan("add role")
    public int add(AddRoleRequest role) {
        int oid = role.getOid();
        RoleExample example = new RoleExample();
        example.createCriteria().andOidEqualTo(oid);
        int id = roleMapper
                .selectByExample(example)
                .stream()
                .mapToInt(RoleKey::getId)
                .max()
                .orElse(0) + 1;
        tracerUtil.getSpan().tag("exist", "false");
        role.getAids().forEach(aid -> {
            Role role1 = new Role();
            role1.withName(role.getName()).withAid(aid).withOid(oid).withId(id);
            roleMapper.insert(role1);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        });
        tracerUtil.getSpan().tag("organization", oid + "");
        role.getPermissions().forEach(permission -> {
            permissionService.addRolePermission(oid, id, permission.getId());
        });
        return id;
    }

    @Override
    @NewSpan("update")
    public boolean update(UpdateRoleRequest updateRole) {
        int oid = updateRole.getOid();
        int rid = updateRole.getId();
        String name = tracerUtil.newSpan("get name", span -> {
            RoleExample example = new RoleExample();
            example.createCriteria().andIdEqualTo(rid);
            return roleMapper.selectByExample(example).get(0).getName();
        });
        tracerUtil.newSpanNRet("add account", span -> updateRole.getAddAids().forEach(aid -> {
            Role role = new Role();
            role.withName(name).withId(rid).withOid(oid).withAid(aid);
            roleMapper.insert(role);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        }));
        tracerUtil.newSpanNRet("del account", span -> updateRole.getDelAids().forEach(aid -> {
            Role role = new Role();
            role.withId(rid).withOid(oid).withAid(aid);
            roleMapper.deleteByPrimaryKey(role);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        }));
        Boolean needDel = tracerUtil.newSpan("need del?", span -> {
            RoleExample example = new RoleExample();
            example.createCriteria().andIdEqualTo(rid);
            return roleMapper.countByExample(example) == 0;
        });
        if (needDel) {
            permissionService.delRole(oid, rid);
            return true;
        }
        tracerUtil.newSpan("add permission",
                (Consumer<ScopedSpan>) span -> updateRole.getAddPids()
                        .forEach(pid -> permissionService.addRolePermission(oid, rid, pid)));
        tracerUtil.newSpan("del permission",
                (Consumer<ScopedSpan>) span -> updateRole.getDelPids()
                        .forEach(pid -> permissionService.delRolePermission(oid, rid, pid)));
        tracerUtil.newSpan("update name", span -> {
            if (updateRole.getName() == null) {
                return;
            }
            Role role = new Role();
            role.withName(updateRole.getName());
            RoleExample example = new RoleExample();
            example.createCriteria().andIdEqualTo(rid);
            roleMapper.updateByExampleSelective(role, example);
        });
        return true;
    }


}
