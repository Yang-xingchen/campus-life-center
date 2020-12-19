package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.AccountOrganization;
import campuslifecenter.usercenter.entry.AccountOrganizationExample;
import campuslifecenter.usercenter.entry.AccountOrganizationKey;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.AccountOrganizationMapper;
import campuslifecenter.usercenter.mapper.OrganizationMapper;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public List<AccountOrganization> role(String aid, int oid) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria().andAidEqualTo(aid).andOidEqualTo(oid);
        return accountOrganizationMapper.selectByExample(example);
    }

    @Override
    public List<AccountInfo> getMember(int id) {
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
}
