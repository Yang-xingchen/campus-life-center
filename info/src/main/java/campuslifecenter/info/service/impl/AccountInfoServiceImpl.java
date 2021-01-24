package campuslifecenter.info.service.impl;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    public List<AccountSaveInfo> getAccountSaveInfo(List<Long> ids, String aid) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andAidEqualTo(aid).andIdIn(ids);
        return saveMapper.selectByExample(example);
    }

    private Consumer<InfoItem> getSubmitConsumer(String aid, String ref) {
        return infoItem -> util.newSpan("get account submit:", span -> {
            AccountSubmitExample example = new AccountSubmitExample();
            example.createCriteria().andRefEqualTo(ref).andIdEqualTo(infoItem.getId()).andAidEqualTo(aid);
            List<AccountSubmit> infoAccountLists = submitMapper.selectByExample(example);
            infoItem.setValue(infoAccountLists.stream().map(AccountSubmit::getText).collect(Collectors.toList()));
        });
    }

    @Override
    public InfoSourceCollect getSubmit(String ref, String aid) {
        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setSource(ref);
        AccountSubmitExample infoListExample = new AccountSubmitExample();
        infoListExample.createCriteria().andRefEqualTo(ref);
        collect.setItems(submitMapper.selectByExample(infoListExample).stream().map(infoList -> {
            InfoItem item = infoService.getCollectItem(infoList.getId(), getSubmitConsumer(aid, ref));
            item.setOrder(infoList.getMultipleIndex());
            return item;
        }).collect(Collectors.toList()));
        return collect;
    }


    @Override
    public InfoSourceCollect getAllAccountSubmit(String ref) {
        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setSource(ref);
        AccountSubmitExample infoListExample = new AccountSubmitExample();
        infoListExample.createCriteria().andRefEqualTo(ref);
        collect.setItems(submitMapper
                .selectByExample(infoListExample)
                .stream()
                .flatMap(infoList -> {
                    AccountSaveInfoExample saveExample = new AccountSaveInfoExample();
                    saveExample.createCriteria().andIdEqualTo(infoList.getId());
                    return saveMapper.selectByExample(saveExample).stream().map(accountInfo -> {
                        String aid = accountInfo.getAid();
                        InfoItem item = infoService.getCollectItem(infoList.getId(), getSubmitConsumer(aid, ref));
                        item.setOrder(infoList.getMultipleIndex());
                        item.setAid(aid);
                        item.setAccountName(cacheService.getAccountNameByID(aid));
                        return item;
                    });
                }).collect(Collectors.toList()));
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
        infos.stream().map(UpdateInfoAccountList::create).forEach(infoDao::insertOrUpdate);
        return true;
    }

    public static class UpdateInfoAccountList extends AccountSubmit {
        private String newText;

        public static UpdateInfoAccountList create(AccountSubmit infoAccountList) {
            UpdateInfoAccountList update = new UpdateInfoAccountList();
            update.setNewText(infoAccountList.getText()).withText(infoAccountList.getText())
                    .withId(infoAccountList.getId())
                    .withAid(infoAccountList.getAid())
                    .withRef(infoAccountList.getRef());
            return update;
        }

        public String getNewText() {
            return newText;
        }

        public UpdateInfoAccountList setNewText(String newText) {
            this.newText = newText;
            return this;
        }
    }
}
