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

    private Consumer<InfoItem> getSubmitConsumer(String aid, String ref) {
        return infoItem -> tracerUtil.newSpan("get submit: " + aid, span -> {
            infoItem.setAid(aid);
            infoItem.setAccountName(cacheService.getAccountNameByID(aid));
            AccountSubmitExample example = new AccountSubmitExample();
            example.createCriteria()
                    .andIdEqualTo(infoItem.getId())
                    .andRefEqualTo(ref)
                    .andAidEqualTo(aid);
            infoItem.setValue(submitMapper.selectByExample(example)
                    .stream().map(AccountSubmit::getText).collect(Collectors.toList()));
        });
    }

    @Override
    @NewSpan("get account ref submit")
    public InfoItem.CompositeItem getSubmit(@SpanTag("ref") String ref, @SpanTag("aid") String aid, @SpanTag("root") long rootId) {
        return (InfoItem.CompositeItem) infoService.getInfoItem(rootId, getSubmitConsumer(aid, ref));
    }


    @Override
    @NewSpan("get all account ref submit")
    public InfoSourceCollect getSubmitByRef(@SpanTag("ref") String ref, @SpanTag("root") long rootId) {
        AccountSubmitExample submitExample = new AccountSubmitExample();
        submitExample.createCriteria().andRefEqualTo(ref).andIdEqualTo(rootId);
        List<AccountSubmit> submits = submitMapper.selectByExample(submitExample);

        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setSource(ref);
        collect.setItems(submits
                .stream()
                .map(submit -> infoService.getInfoItem(submit.getId(),getSubmitConsumer(submit.getAid(), ref)))
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
        // TODO 删除已提交
        infos.stream().map(UpdateSubmit::create).forEach(infoDao::insertOrUpdate);
        return true;
    }

    public static class UpdateSubmit extends AccountSubmit {
        private String newText;

        public static UpdateSubmit create(AccountSubmit accountSubmit) {
            UpdateSubmit update = new UpdateSubmit();
            update.setNewText(accountSubmit.getText())
                    .withText(accountSubmit.getText())
                    .withId(accountSubmit.getId())
                    .withAid(accountSubmit.getAid())
                    .withMultipleIndex(accountSubmit.getMultipleIndex())
                    .withRef(accountSubmit.getRef());
            return update;
        }

        public String getNewText() {
            return newText;
        }

        public UpdateSubmit setNewText(String newText) {
            this.newText = newText;
            return this;
        }
    }
}
