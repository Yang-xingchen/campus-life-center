package campuslifecenter.usercenter.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.AccountOrganizationRoleMapper;
import campuslifecenter.usercenter.mapper.RoleMapper;
import campuslifecenter.usercenter.model.AddRoleRequest;
import campuslifecenter.usercenter.model.RoleInfo;
import campuslifecenter.usercenter.model.UpdateRoleRequest;
import campuslifecenter.usercenter.service.PermissionService;
import campuslifecenter.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private AccountOrganizationRoleMapper accountRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TracerUtil tracerUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${user-center.redis.cache.account-info}")
    private String ACCOUNT_INFO;

    @Override
    @NewSpan("role")
    public List<RoleInfo> getRole(@SpanTag("account") String aid, @SpanTag("organization") int oid) {
        AccountOrganizationRoleExample example = new AccountOrganizationRoleExample();
        example.createCriteria().andAidEqualTo(aid).andOidEqualTo(oid);
        return accountRoleMapper.selectByExample(example)
                .stream()
                .map(ar -> {
                    RoleInfo role = new RoleInfo();
                    role.setOid(oid)
                            .withId(ar.getId())
                            .withName(roleMapper.selectByPrimaryKey(ar.getId()).getName());
                    role.setPermissions(permissionService.getPermission(oid, ar.getId()));
                    return role;
                }).collect(Collectors.toList());
    }

    @Override
    @NewSpan("add role")
    public int add(AddRoleRequest roleRequest) {
        int oid = roleRequest.getOid();
        int rid = tracerUtil.newSpan("insert role" ,span -> {
            Role role = new Role();
            role.withName(roleRequest.getName());
            roleMapper.insert(role);
            return role.getId();
        });
        roleRequest.getAids().forEach(aid -> {
            AccountOrganizationRoleKey accountRole = new AccountOrganizationRoleKey();
            accountRole.withAid(aid).withOid(oid).withId(rid);
            accountRoleMapper.insert(accountRole);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        });
        tracerUtil.getSpan().tag("organization", oid + "");
        tracerUtil.getSpan().tag("role", rid + "");
        roleRequest.getPermissions().forEach(permission -> {
            permissionService.addRolePermission(oid, rid, permission.getId());
        });
        return rid;
    }

    @Override
    @NewSpan("update")
    public boolean update(UpdateRoleRequest updateRole) {
        int oid = updateRole.getOid();
        int rid = updateRole.getId();
        tracerUtil.newSpan("update role name", span -> {
            Role role = new Role();
            role.withName(updateRole.getName()).withId(rid);
            roleMapper.updateByPrimaryKey(role);
        });
        tracerUtil.newSpanNRet("add account", span -> updateRole.getAddAids().forEach(aid -> {
            AccountOrganizationRoleKey aor = new AccountOrganizationRoleKey();
            aor.withAid(aid).withOid(oid).withId(rid);
            accountRoleMapper.insert(aor);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        }));
        tracerUtil.newSpanNRet("del account", span -> updateRole.getDelAids().forEach(aid -> {
            AccountOrganizationRoleKey aor = new AccountOrganizationRoleKey();
            aor.withAid(aid).withOid(oid).withId(rid);
            accountRoleMapper.deleteByPrimaryKey(aor);
            redisTemplate.delete(ACCOUNT_INFO + aid);
        }));
        Boolean needDel = tracerUtil.newSpan("need del?", span -> {
            AccountOrganizationRoleExample example = new AccountOrganizationRoleExample();
            example.createCriteria().andOidEqualTo(oid).andIdEqualTo(rid);
            return accountRoleMapper.countByExample(example) == 0;
        });
        if (needDel) {
            permissionService.delRoleAllPermission(oid, rid);
            roleMapper.deleteByPrimaryKey(rid);
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
