package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.AccountSubscribeExample;
import campuslifecenter.notice.entry.AccountSubscribeKey;
import campuslifecenter.notice.mapper.AccountSubscribeMapper;
import campuslifecenter.notice.model.PublishObserveRequest;
import campuslifecenter.notice.service.OrganizationSubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class OrganizationSubscribeServiceImpl implements OrganizationSubscribeService {
    @Autowired
    private AccountSubscribeMapper accountSubscribeMapper;

    @Autowired
    @Qualifier(NoticeStream.PUBLISH_OBSERVE)
    private MessageChannel messageChannel;

    @Override
    @NewSpan("get organization subscribe")
    public List<String> getSubscribeAccountId(int id) {
        AccountSubscribeExample example = new AccountSubscribeExample();
        example.createCriteria().andOidEqualTo(id);
        return accountSubscribeMapper
                .selectByExample(example)
                .stream()
                .map(AccountSubscribeKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public boolean subscribe(String aid, int oid) {
        AccountSubscribeKey accountSubscribe = new AccountSubscribeKey();
        accountSubscribe.withAid(aid).withOid(oid);
        accountSubscribeMapper.insert(accountSubscribe);
        PublishObserveRequest request = new PublishObserveRequest();
        request.setType(PublishObserveRequest.ORGANIZATION).setAid(aid).setOid(oid).setSubscribe(true);
        messageChannel.send(MessageBuilder.withPayload(request).build());
        return true;
    }

    @Override
    public boolean cancelSubscribe(String aid, int oid) {
        AccountSubscribeKey accountSubscribe = new AccountSubscribeKey();
        accountSubscribe.withAid(aid).withOid(oid);
        accountSubscribeMapper.deleteByPrimaryKey(accountSubscribe);
        return true;
    }

}
