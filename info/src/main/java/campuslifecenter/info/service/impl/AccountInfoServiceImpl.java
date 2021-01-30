package campuslifecenter.info.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.info.dao.InfoDao;
import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.AccountSaveInfoMapper;
import campuslifecenter.info.mapper.AccountSubmitMapper;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountSubmitMapper submitMapper;
    @Autowired
    private AccountSaveInfoMapper saveMapper;
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
        return saveMapper.selectByExample(example);
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
        return saveMapper
                .selectByExample(example)
                .stream()
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("submit")
    public Boolean submit(List<AccountSubmit> infos) {
        infos.stream()
                .collect(Collectors.groupingBy(submit -> new AccountSubmitKey()
                        .withRoot(submit.getRoot())
                        .withAid(submit.getAid())
                        .withId(submit.getId())))
                .forEach((key, submits) -> {
                    AccountSubmitExample example = new AccountSubmitExample();
                    example.createCriteria()
                            .andRootEqualTo(key.getRoot()).andIdEqualTo(key.getId()).andAidEqualTo(key.getAid());
                    submitMapper.deleteByExample(example);
                    submits.forEach(submitMapper::insert);
                    tracerUtil.newSpan("save", span -> {
                        // TODO save
                    });
                });
        return true;
    }

}
