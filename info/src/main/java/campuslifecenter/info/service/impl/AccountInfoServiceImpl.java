package campuslifecenter.info.service.impl;

import brave.Span;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.info.dao.InfoDao;
import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.AccountSaveInfoMapper;
import campuslifecenter.info.mapper.AccountSubmitMapper;
import campuslifecenter.info.mapper.OrganizationSaveInfoMapper;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountSubmitMapper submitMapper;
    @Autowired
    private AccountSaveInfoMapper accountSaveMapper;
    @Autowired
    private OrganizationSaveInfoMapper organizationSaveMapper;
    @Autowired
    private InfoDao infoDao;
    @Autowired
    private InfoService infoService;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    @Override
    @NewSpan("get save")
    public List<AccountSaveInfo> getSaveByAccount(List<Long> ids, @SpanTag("aid") String aid) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andAidEqualTo(aid).andIdIn(ids);
        return accountSaveMapper.selectByExample(example);
    }

    @Override
    public List<OrganizationSaveInfo> getSaveByOrganization(List<Long> ids, int id) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        OrganizationSaveInfoExample example = new OrganizationSaveInfoExample();
        example.createCriteria().andOidEqualTo(id).andIdIn(ids);
        return organizationSaveMapper.selectByExample(example);
    }

    private Consumer<InfoItem> getSubmitConsumer(String aid, long root) {
        return infoItem -> tracerUtil.newSpan("get submit: " + aid, span -> {
            infoItem.setAid(aid);
            infoItem.setAccountName(cacheService.getAccountNameByID(aid));
            AccountSubmitExample example = new AccountSubmitExample();
            example.createCriteria()
                    .andIdEqualTo(infoItem.getId())
                    .andRootEqualTo(root)
                    .andAidEqualTo(aid);
            infoItem.setValue(submitMapper.selectByExample(example)
                    .stream().map(AccountSubmit::getText).collect(Collectors.toList()));
        });
    }

    @Override
    @NewSpan("get account ref submit")
    public InfoItem.CompositeItem getSubmit(@SpanTag("aid") String aid, @SpanTag("root") long rootId) {
        return (InfoItem.CompositeItem) infoService.getInfoItem(rootId, getSubmitConsumer(aid, rootId));
    }


    @Override
    @NewSpan("get all account ref submit")
    public InfoSourceCollect getSubmitByRoot(@SpanTag("root") long rootId) {
        AccountSubmitExample submitExample = new AccountSubmitExample();
        submitExample.createCriteria().andIdEqualTo(rootId);
        List<AccountSubmit> submits = submitMapper.selectByExample(submitExample);

        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setItems(submits
                .stream()
                .map(submit -> infoService.getInfoItem(submit.getId(),getSubmitConsumer(submit.getAid(), rootId)))
                .collect(Collectors.toList()));
        return collect;
    }

    @Override
    @NewSpan("select")
    public List<String> select(@SpanTag("id") long id, @SpanTag("text") String text) {
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andIdEqualTo(id).andTextLike(text);
        return accountSaveMapper
                .selectByExample(example)
                .stream()
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("submit")
    public Boolean submit(List<AccountSubmit> infos) {
        infos.stream()
                .collect(Collectors.groupingBy(AccountSubmitKey::getId))
                .forEach((id, submits) -> {
                    AccountSubmit submit = submits.get(0);
                    String aid = submit.getAid();
                    long root = submit.getRoot();
                    Span itemSpan = tracerUtil.getSpan();
                    itemSpan.tag("aid", aid);
                    itemSpan.tag("id", id + "");
                    // delete and insert submit.
                    AccountSubmitExample example = new AccountSubmitExample();
                    example.createCriteria().andRootEqualTo(root).andIdEqualTo(id).andAidEqualTo(aid);
                    submitMapper.deleteByExample(example);
                    submits.forEach(submitMapper::insert);
                    // update save.
                    infoService.getInfoItem(id, infoItem -> tracerUtil.newSpan("save: " + infoItem.getId(), span -> {
                        if (infoItem.getType() == 1) {
                            span.annotate("skip composite");
                            return;
                        }
                        AccountSaveInfoExample saveExample = new AccountSaveInfoExample();
                        Function<String, AccountSaveInfo> toSave = s -> {
                            AccountSaveInfo accountSaveInfo = new AccountSaveInfo();
                            accountSaveInfo
                                    .withText(s)
                                    .withCode(false)
                                    .withVisibility(infoItem.getDefaultVisibility())
                                    .withAid(aid)
                                    .withId(id);
                            return accountSaveInfo;
                        };
                        if (infoItem.getMultiple()) {
                            saveExample.createCriteria()
                                    .andAidEqualTo(aid).andIdEqualTo(id);
                            List<AccountSaveInfo> exist = accountSaveMapper.selectByExample(saveExample);
                            span.annotate("select finish");
                            accountSaveMapper.deleteByExample(saveExample);
                            span.annotate("delete finish");
                            List<String> existText = exist.stream().map(AccountSaveInfo::getText).collect(Collectors.toList());
                            Stream<AccountSaveInfo> add = submits.stream()
                                    .map(AccountSubmit::getText)
                                    .filter(s -> !existText.contains(s))
                                    .map(toSave);
                            AtomicInteger index = new AtomicInteger(0);
                            span.annotate("collect finish");
                            Stream.concat(exist.stream(), add)
                                    .peek(save -> save.withVisibility(index.incrementAndGet()))
                                    .forEach(accountSaveMapper::insert);
                        } else {
                            saveExample.createCriteria()
                                    .andAidEqualTo(aid).andIdEqualTo(id);
                            accountSaveMapper.deleteByExample(saveExample);
                            span.annotate("delete finish");
                            AccountSaveInfo accountSaveInfo = toSave.apply(submit.getText());
                            accountSaveInfo.withMultipleIndex(0);
                            accountSaveMapper.insert(accountSaveInfo);
                        }
                    }));
                });
        return true;
    }

}
