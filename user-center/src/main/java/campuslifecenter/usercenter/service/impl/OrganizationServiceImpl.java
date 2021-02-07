package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.AccountOrganizationMapper;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private AccountOrganizationMapper accountOrganizationMapper;
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
        example.createCriteria().andAidEqualTo(aid);
        return accountOrganizationMapper.selectByExample(example)
                .stream()
                .collect(Collectors.groupingBy(AccountOrganization::getOid))
                .entrySet()
                .stream()
                .map(entry -> tracerUtil.newSpan("get organization", span -> {
                    int id = entry.getKey();
                    span.tag("id", id + "");
                    Organization organization = organizationMapper.selectByPrimaryKey(id);
                    OrganizationInfo info = new OrganizationInfo();
                    info.setId(id).setName(organization.getName()).setType(organization.getType());
                    List<RoleInfo> roles = entry.getValue()
                            .stream()
                            .map(accountOrganization -> tracerUtil.newSpan("get roles", rSpan -> {
                                int rid = accountOrganization.getRole();
                                rSpan.tag("id", rid + "");
                                span.annotate("handle role permission: " + accountOrganization.getRoleName());
                                RoleInfo role = new RoleInfo();
                                role.setId(rid).setName(accountOrganization.getRoleName());
                                List<Permission> permissions = permissionService.getPermission(id, rid);
                                role.setPermissions(permissions);
                                return role;
                            }))
                            .collect(Collectors.toList());
                    info.setRoles(roles);
                    return info;
                }))
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("role")
    public List<RoleInfo> getRole(@SpanTag("account") String aid, @SpanTag("organization") int oid) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andAidEqualTo(aid).andOidEqualTo(oid);
        return accountOrganizationMapper.selectByExample(example)
                .stream()
                .map(ao -> {
                    int rid = ao.getRole();
                    RoleInfo role = new RoleInfo();
                    role.setId(rid).setName(ao.getRoleName());
                    role.setPermissions(permissionService.getPermission(ao.getOid(), rid));
                    return role;
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member")
    public List<AccountInfo> getMember(@SpanTag("id") int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andOidEqualTo(id);
        return accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .collect(Collectors.groupingBy(AccountOrganizationKey::getAid))
                .entrySet()
                .stream()
                .map(entry -> {
                    String aid = entry.getKey();
                    tracerUtil.getSpan().annotate("handle account:" + aid);
                    Account account = accountMapper.selectByPrimaryKey(aid);
                    AccountInfo info = AccountInfo.withAccount(account);
                    List<RoleInfo> roles = entry.getValue().stream().map(accountOrganization ->
                            new RoleInfo().setName(accountOrganization.getRoleName()).setId(accountOrganization.getRole())
                    ).collect(Collectors.toList());
                    info.setOrganizations(List.of(
                            new OrganizationInfo().setId(id).setRoles(roles)
                    ));
                    return info;
                })
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get member id")
    public List<String> getMemberId(int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andOidEqualTo(id);
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

}
