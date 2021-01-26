package campuslifecenter.info.service.impl;

import brave.ScopedSpan;
import campuslifecenter.info.component.Util;
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
    private Util util;

    @Override
    public List<AccountSaveInfo> getSaveByAccount(List<Long> ids, String aid) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andAidEqualTo(aid).andIdIn(ids);
        return saveMapper.selectByExample(example);
    }

    private Consumer<InfoItem> getSubmitConsumer(String aid, String ref) {
        return infoItem -> util.newSpan("get submit: " + aid, span -> {
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
    public InfoItem.CompositeItem getSubmit(String ref, String aid, long rootId) {
        return (InfoItem.CompositeItem) infoService.getInfoItem(rootId, getSubmitConsumer(aid, ref));
    }


    @Override
    public InfoSourceCollect getSubmitByRef(String ref, long rootId) {
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
    public List<String> select(long id, String text) {
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andIdEqualTo(id).andTextLike(text);
        return saveMapper
                .selectByExample(example)
                .stream()
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    @Override
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
