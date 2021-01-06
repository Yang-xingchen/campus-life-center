package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.AccountSubscribeExample;
import campuslifecenter.notice.entry.AccountSubscribeKey;
import campuslifecenter.notice.mapper.AccountSubscribeMapper;
import campuslifecenter.notice.service.OrganizationSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrganizationSubscribeServiceImpl implements OrganizationSubscribeService {
    @Autowired
    private AccountSubscribeMapper accountSubscribeMapper;

    @Override
    public List<String> getSubscribeAccountId(int id) {
        AccountSubscribeExample example = new AccountSubscribeExample();
        example.createCriteria().andOidEqualTo(id);
        return accountSubscribeMapper
                .selectByExample(example)
                .stream()
                .map(AccountSubscribeKey::getAid)
                .collect(Collectors.toList());
    }
}
