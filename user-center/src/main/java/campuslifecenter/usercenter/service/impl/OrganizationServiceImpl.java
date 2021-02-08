package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.AccountOrganizationMapper;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
import campuslifecenter.usercenter.mapper.RoleMapper;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.OrganizationInfo;
import campuslifecenter.usercenter.model.RoleInfo;
import campuslifecenter.usercenter.service.OrganizationService;
import campuslifecenter.usercenter.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AccountOrganizationMapper accountOrganizationMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    @Value("${user-center.cache.organization-name}")
    public String ORGANIZATION_NAME_PREFIX = "organizationNameCache:";

    @Override
    public Organization get(int id) {
        Organization organization = organizationMapper.selectByPrimaryKey(id);
        redisTemplate.opsForValue()
                .set(ORGANIZATION_NAME_PREFIX + organization.getId(), organization.getName(),
                        1, TimeUnit.HOURS);
        return organization;
    }

    @Override
    @NewSpan("organization")
    public List<OrganizationInfo> getOrganization(@SpanTag("account") String aid) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria()
                .andAidEqualTo(aid)
                .andAccountAcceptEqualTo(true)
                .andOrganizationAcceptEqualTo(true);
        return accountOrganizationMapper.selectByExample(example)
                .stream()
                .map(accountOrganization -> tracerUtil.newSpan("get organization", span -> {
                    int id = accountOrganization.getOid();
                    span.tag("organization", id + "");
                    Organization organization = organizationMapper.selectByPrimaryKey(id);
                    OrganizationInfo info = new OrganizationInfo();
                    info.setId(id).setName(organization.getName()).setType(organization.getType());
                    RoleExample roleExample = new RoleExample();
                    roleExample.createCriteria().andAidEqualTo(aid).andOidEqualTo(id);
                    List<RoleInfo> roles = roleMapper.selectByExample(roleExample)
                            .stream()
                            .map(role -> {
                                RoleInfo roleInfo = new RoleInfo();
                                roleInfo.withRoleName(role.getRoleName())
                                        .withAid(aid)
                                        .withOid(id)
                                        .withRole(role.getRole());
                                roleInfo.setPermissions(permissionService.getPermission(id, role.getRole()));
                                return roleInfo;
                            })
                            .collect(Collectors.toList());
                    info.setRoles(roles);
                    return info;
                }))
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("role")
    public List<RoleInfo> getRole(@SpanTag("account") String aid, @SpanTag("organization") int oid) {
        RoleExample example = new RoleExample();
        example.createCriteria().andAidEqualTo(aid).andOidEqualTo(oid);
        return roleMapper.selectByExample(example)
                .stream()
                .map(ar -> {
                    int rid = ar.getRole();
                    RoleInfo role = new RoleInfo();
                    role.withRoleName(ar.getRoleName()).withRole(rid);
                    role.setPermissions(permissionService.getPermission(ar.getOid(), rid));
                    return role;
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member")
    public List<AccountInfo> getMember(@SpanTag("id") int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria()
                .andOidEqualTo(id)
                .andHideEqualTo(false)
                .andAccountAcceptEqualTo(true)
                .andOrganizationAcceptEqualTo(true);
        return accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(accountOrganization -> {
                    String aid = accountOrganization.getAid();
                    tracerUtil.getSpan().annotate("handle account:" + aid);
                    Account account = accountMapper.selectByPrimaryKey(aid);
                    return AccountInfo.withAccount(account);
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member id")
    public List<String> getMemberId(int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria()
                .andOidEqualTo(id)
                .andAccountAcceptEqualTo(true)
                .andOrganizationAcceptEqualTo(true);
        return accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(AccountOrganizationKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public List<Organization> getPublicOrganization() {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andHideEqualTo(false);
        return organizationMapper.selectByExample(example);
    }

    @Override
    public List<Organization> getChild(int id) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andParentEqualTo(id);
        return organizationMapper.selectByExample(example);
    }

    @Override
    public Organization getParent(int id) {
        Integer parent = organizationMapper.selectByPrimaryKey(id).getParent();
        if (parent == null) {
            return new Organization();
        }
        return organizationMapper.selectByPrimaryKey(parent);
    }

    @Override
    public int add(Organization organization) {
        organizationMapper.insert(organization);
        Role role = new Role();
        role.withRoleName("创建者").withAid(organization.getCreator()).withOid(organization.getId()).withRole(1);
        roleMapper.insert(role);
        IntStream.of(2, 3, 4, 5, 6, 11)
                .forEach(pid -> permissionService.addRolePermission(organization.getId(), 1, pid));
        return organization.getId();
    }

    @Override
    public boolean invite(int id, List<String> aids) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andAidIn(aids).andOidEqualTo(id);
        Set<String> existAids = accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(AccountOrganizationKey::getAid)
                .collect(Collectors.toSet());
        aids.forEach(aid -> {
            AccountOrganization accountOrganization = new AccountOrganization();
            accountOrganization
                    .withOrganizationAccept(true)
                    .withAid(aid)
                    .withOid(id);
            if (existAids.contains(aid)) {
                accountOrganizationMapper.updateByPrimaryKeySelective(accountOrganization);
            } else {
                accountOrganizationMapper.insertSelective(accountOrganization);
            }
        });
        return true;
    }

    @Override
    public boolean apply(int id, String aid) {
        AccountOrganization accountOrganization = new AccountOrganization();
        accountOrganization
                .withAccountAccept(true)
                .withAid(aid)
                .withOid(id);
        if (accountOrganizationMapper.selectByPrimaryKey(accountOrganization) != null) {
            accountOrganizationMapper.updateByPrimaryKeySelective(accountOrganization);
        } else {
            accountOrganizationMapper.insertSelective(accountOrganization);
        }
        return true;
    }

    @Override
    public List<AccountOrganization> applyList(int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andOidEqualTo(id)
                .andAccountAcceptEqualTo(true).andOrganizationAcceptEqualTo(false);
        return accountOrganizationMapper.selectByExample(example);
    }

}
