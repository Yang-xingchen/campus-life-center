package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.AccountOrganizationExample;
import campuslifecenter.usercenter.entry.AccountOrganizationKey;
import campuslifecenter.usercenter.entry.Organization;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.AccountOrganizationMapper;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.service.OrganizationService;
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
    private RedisTemplate<String, String> redisTemplate;

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
    @NewSpan("get role")
    public List<AccountOrganization> role(@SpanTag("id") String aid, @SpanTag("organization") int oid) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andAidEqualTo(aid).andOidEqualTo(oid);
        return accountOrganizationMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get member")
    public List<AccountInfo> getMember(@SpanTag("id") int id) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andOidEqualTo(id);
        return accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(AccountOrganizationKey::getAid)
                .map(accountMapper::selectByPrimaryKey)
                .map(AccountInfo::withAccount)
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

}
