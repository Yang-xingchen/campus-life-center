package campuslifecenter.usercenter.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.usercenter.component.AccountStream;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.AccountOrganizationMapper;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
import campuslifecenter.usercenter.mapper.RoleMapper;
import campuslifecenter.usercenter.model.*;
import campuslifecenter.usercenter.service.OrganizationService;
import campuslifecenter.usercenter.service.PermissionService;
import campuslifecenter.usercenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
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
    private AccountMapper accountMapper;

    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    @Autowired
    @Qualifier(AccountStream.PUBLISH_OBSERVE)
    private MessageChannel messageChannel;

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
                    info.setRoles(roleService.getRole(aid, id));
                    return info;
                }))
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
                    OrganizationInfo organizationInfo = new OrganizationInfo();
                    organizationInfo.setId(id).setRoles(roleService.getRole(accountInfo.getId(), id));
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

    private final static List<Permission> CREATOR_PERMISSIONS = IntStream.of(101, 102, 103, 205)
            .mapToObj(pid -> new Permission().withId(pid))
            .collect(Collectors.toList());

    @Override
    @NewSpan("add")
    public int add(Organization organization) {
        tracerUtil.newSpan("insert organization", span -> {
            organizationMapper.insert(organization);
        });
        int oid = organization.getId();
        String aid = organization.getCreator();
        tracerUtil.getSpan().tag("account", aid);
        tracerUtil.getSpan().tag("organization", oid+"");
        tracerUtil.newSpan("insert creator", span -> {
            AccountOrganization accountOrganization = new AccountOrganization();
            accountOrganization.withOrganizationAccept(true).withAccountAccept(true)
                    .withHide(false)
                    .withAid(aid).withOid(oid);
            accountOrganizationMapper.insertSelective(accountOrganization);
        });
        tracerUtil.newSpan("insert role", span -> {
            AddRoleRequest addRole = new AddRoleRequest();
            addRole.withName("创建者");
            addRole.setOid(oid).setPermissions(CREATOR_PERMISSIONS);
            addRole.setAids(List.of(aid));
            roleService.add(addRole);
        });
        return oid;
    }

    @Override
    @NewSpan("invite")
    public boolean invite(@SpanTag("organization") int id, List<String> aids) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andAidIn(aids).andOidEqualTo(id).andAccountAcceptEqualTo(true);
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
                PublishObserveRequest request = new PublishObserveRequest();
                request.setAid(aid).setOid(id).setBelong(true);
                messageChannel.send(MessageBuilder.withPayload(request).build());
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
        AccountOrganization exist = accountOrganizationMapper.selectByPrimaryKey(accountOrganization);
        if (exist != null) {
            accountOrganizationMapper.updateByPrimaryKeySelective(accountOrganization);
            if (exist.getOrganizationAccept()) {
                PublishObserveRequest request = new PublishObserveRequest();
                request.setAid(aid).setOid(id).setBelong(true);
                messageChannel.send(MessageBuilder.withPayload(request).build());
            }
        } else {
            accountOrganizationMapper.insertSelective(accountOrganization);
        }
        return true;
    }

    @Override
    @NewSpan("add account input system")
    public boolean addAccount(List<String> aids) {
        aids.forEach(id -> {
            AccountOrganization accountOrganization = new AccountOrganization();
            accountOrganization
                    .withAccountAccept(true).withOrganizationAccept(true).withHide(false)
                    .withAid(id).withOid(1);
            accountOrganizationMapper.insert(accountOrganization);
        });
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
