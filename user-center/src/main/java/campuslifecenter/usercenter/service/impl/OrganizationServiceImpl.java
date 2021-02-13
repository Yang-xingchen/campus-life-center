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

    @Value("${user-center.cache.account-info}")
    private String ACCOUNT_INFO;

    @Override
    @NewSpan("get organization")
    public Organization get(@SpanTag("organization") int id) {
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
                                roleInfo.withName(role.getName())
                                        .withAid(aid)
                                        .withOid(id)
                                        .withId(role.getId());
                                roleInfo.setPermissions(permissionService.getPermission(id, role.getId()));
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
                    int rid = ar.getId();
                    RoleInfo role = new RoleInfo();
                    role.withName(ar.getName()).withId(rid);
                    role.setPermissions(permissionService.getPermission(ar.getOid(), rid));
                    return role;
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member")
    public List<AccountInfo> getMember(@SpanTag("id") int id, @SpanTag("show hide") boolean showHide) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        final AccountOrganizationExample.Criteria criteria = example.createCriteria();
        criteria
                .andOidEqualTo(id)
                .andAccountAcceptEqualTo(true)
                .andOrganizationAcceptEqualTo(true);
        if (!showHide) {
            criteria.andHideEqualTo(false);
        }
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
    @NewSpan("get member info")
    public List<AccountInfo> getMemberInfo(@SpanTag("organization") int id, @SpanTag("show hide") boolean showHide) {
        return getMember(id, showHide)
                .stream()
                .peek(accountInfo -> {
                    RoleExample roleExample = new RoleExample();
                    roleExample.createCriteria().andAidEqualTo(accountInfo.getId()).andOidEqualTo(id);
                    List<RoleInfo> roleInfos = roleMapper.selectByExample(roleExample)
                            .stream()
                            .map(role -> {
                                RoleInfo roleInfo = new RoleInfo();
                                roleInfo.withName(role.getName())
                                        .withAid(accountInfo.getId())
                                        .withOid(id)
                                        .withId(role.getId());
                                roleInfo.setPermissions(permissionService.getPermission(id, role.getId()));
                                return roleInfo;
                            })
                            .collect(Collectors.toList());
                    OrganizationInfo organizationInfo = new OrganizationInfo();
                    organizationInfo.setId(id).setRoles(roleInfos);
                    accountInfo.setOrganizations(List.of(organizationInfo));
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member id")
    public List<String> getMemberId(@SpanTag("organization") int id) {
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
    @NewSpan("get public")
    public List<Organization> getPublicOrganization() {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andHideEqualTo(false);
        return organizationMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get child")
    public List<Organization> getChild(@SpanTag("organization") int id) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andParentEqualTo(id);
        return organizationMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get parent")
    public Organization getParent(@SpanTag("organization") int id) {
        Integer parent = organizationMapper.selectByPrimaryKey(id).getParent();
        if (parent == null) {
            return new Organization();
        }
        Organization organization = organizationMapper.selectByPrimaryKey(parent);
        if (organization.getHide()) {
            return new Organization();
        }
        return organization;
    }

    @Override
    @NewSpan("add")
    public int add(Organization organization) {
        organizationMapper.insert(organization);
        AccountOrganization accountOrganization = new AccountOrganization();
        int oid = organization.getId();
        String aid = organization.getCreator();
        tracerUtil.getSpan().tag("account", aid);
        accountOrganization.withOrganizationAccept(true).withAccountAccept(true)
                .withHide(false)
                .withAid(aid).withOid(oid);
        accountOrganizationMapper.insertSelective(accountOrganization);
        Role role = new Role();
        role.withName("创建者").withAid(aid).withOid(oid).withId(0);
        roleMapper.insert(role);
        IntStream.of(101, 102, 103, 205)
                .forEach(pid -> permissionService.addRolePermission(oid, role.getId(), pid));
        redisTemplate.delete(ACCOUNT_INFO + organization.getCreator());
        return oid;
    }

    @Override
    @NewSpan("invite")
    public boolean invite(@SpanTag("organization") int id, List<String> aids) {
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
    @NewSpan("apply")
    public boolean apply(@SpanTag("organization") int id, @SpanTag("account") String aid) {
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
    @NewSpan("get apply list")
    public List<AccountOrganization> applyList(@SpanTag("organization") int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andOidEqualTo(id)
                .andAccountAcceptEqualTo(true).andOrganizationAcceptEqualTo(false);
        return accountOrganizationMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get organization types")
    public List<String> getTypes() {
        return organizationMapper
                .selectByExample(new OrganizationExample())
                .stream()
                .map(Organization::getType)
                .distinct()
                .collect(Collectors.toList());
    }

}
