package campuslifecenter.info.service.impl;

import campuslifecenter.info.component.Util;
import campuslifecenter.info.dao.InfoDao;
import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.*;
import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoTextMapper textMapper;
    @Autowired
    private InfoCompositeMapper compositeMapper;
    @Autowired
    private InfoRadioMapper radioMapper;

    @Autowired
    private InfoListMapper listMapper;

    @Autowired
    private InfoListMapper infoListMapper;
    @Autowired
    private InfoAccountListMapper accountListMapper;
    @Autowired
    private InfoDao infoDao;
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private CacheService cacheService;

    @Autowired
    private Util util;

    private static final String[] TYPE_MAP = new String[]{"文本", "组合", "单选"};

    @Override
    public String addInfoCollect(AddInfoRequest addInfoRequest) {
        String uuid = UUID.randomUUID().toString();
        addInfoRequest.getInfos()
                .forEach(infoCollect -> insertCollect(infoCollect, addInfoRequest.getAids(), uuid));
        return uuid;
    }

    private long insertCollect(AddInfoRequest.InfoCollect infoCollect, List<String> aids, String ref) {
        if (!infoCollect.isExist()) {
            util.newSpan("insert not exist info.", span -> {
                span.tag("type", TYPE_MAP[infoCollect.getType()]);
                Info info = infoCollect.toInfo(ref);
                infoMapper.insert(info);
                infoCollect.setId(info.getId());
                switch (infoCollect.getType()) {
                    case 0 -> textMapper.insert(infoCollect.toText());
                    case 1 -> infoCollect
                            .getCompositeInfo()
                            .stream()
                            .map(item -> {
                                long id = insertCollect(item, aids, ref);
                                return new InfoComposite().withId(id).withPid(infoCollect.getId());
                            })
                            .forEach(compositeMapper::insert);
                    case 2 -> infoCollect.getRadioInfo().forEach(s -> radioMapper.insert(
                            new InfoRadioKey().withId(infoCollect.getId()).withText(s)
                    ));
                    default -> throw new IllegalArgumentException("illegal info type.");
                }
            });
        } else {
            Info info = infoMapper.selectByPrimaryKey(infoCollect.getId());
            infoCollect.setDefaultVisibility(info.getDefaultVisibility());
        }
        Long infoListId = util.newSpan("insert info list", span -> {
            span.tag("name", infoCollect.getName());
            span.tag("ref", ref);
            span.tag("order", infoCollect.getOrder() + "");
            InfoList infoList = new InfoList().withListOrder(infoCollect.getOrder());
            infoList.withId(infoCollect.getId()).withSource(ref);
            infoListMapper.insert(infoList);
            return infoList.getId();
        });
        aids.forEach(s -> util.newSpan("insert account: " + s, span -> {
            AccountInfo accountInfo = new AccountInfo()
                    .withVisibility(infoCollect.getDefaultVisibility());
            accountInfo.withAid(s).withId(infoListId);
            accountInfoMapper.insert(accountInfo);
        }));
        return infoCollect.getId();
    }

    @Override
    public List<String> select(long id, String text) {
        AccountInfoExample example = new AccountInfoExample();
        example.createCriteria().andIdEqualTo(id).andTextLike(text);
        return accountInfoMapper
                .selectByExample(example)
                .stream()
                .map(AccountInfoKey::getAid)
                .collect(Collectors.toList());
    }

    @Override
    public InfoSourceCollect getAllAccountSubmit(String ref) {
        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setSource(ref);
        InfoListExample infoListExample = new InfoListExample();
        infoListExample.createCriteria().andSourceEqualTo(ref);
        collect.setItems(infoListMapper.selectByExample(infoListExample).stream().flatMap(infoList -> {
            AccountInfoExample example = new AccountInfoExample();
            example.createCriteria().andIdEqualTo(infoList.getId());
            return accountInfoMapper.selectByExample(example).stream().map(accountInfo -> {
                String aid = accountInfo.getAid();
                InfoItem item = getCollectItem(ref, infoList.getId(), aid);
                item.setOrder(infoList.getListOrder());
                item.setAid(aid);
                item.setAccountName(cacheService.getAccountNameByID(aid));
                return item;
            });
        }).collect(Collectors.toList()));
        return collect;
    }

    @Override
    public InfoSourceCollect getCollectList(String ref, String aid) {
        InfoSourceCollect collect = new InfoSourceCollect();
        collect.setSource(ref);
        InfoListExample infoListExample = new InfoListExample();
        infoListExample.createCriteria().andSourceEqualTo(ref);
        collect.setItems(infoListMapper.selectByExample(infoListExample).stream().map(infoList -> {
            InfoItem item = getCollectItem(ref, infoList.getId(), aid);
            item.setOrder(infoList.getListOrder());
            return item;
        }).collect(Collectors.toList()));
        return collect;
    }

    private InfoItem getCollectItem(String ref, long id, String aid) {
        InfoItem item = util.newSpan("get info: " + id, span -> {
            Info info = infoMapper.selectByPrimaryKey(id);
            span.tag("type", TYPE_MAP[info.getType()]);
            span.tag("name", info.getName());
            InfoItem collectItem = InfoItem.create(info);
            switch (info.getType()) {
                case 0 -> ((InfoItem.TextItem) collectItem)
                        .setSample(textMapper.selectByPrimaryKey(id).getSample());
                case 1 -> {
                    InfoItem.CompositeItem aItem = (InfoItem.CompositeItem) collectItem;
                    InfoCompositeExample arrayExample = new InfoCompositeExample();
                    arrayExample.createCriteria().andPidEqualTo(id);
                    aItem.setItems(compositeMapper.selectByExample(arrayExample)
                            .stream().map(InfoComposite::getId).map(i->getCollectItem(ref, i, aid))
                            .collect(Collectors.toList()));
                }
                case 2 -> {
                    InfoItem.RadioItem rItem = (InfoItem.RadioItem) collectItem;
                    InfoRadioExample radioExample = new InfoRadioExample();
                    radioExample.createCriteria().andIdEqualTo(id);
                    rItem.setRadio(radioMapper.selectByExample(radioExample)
                            .stream().map(InfoRadioKey::getText).collect(Collectors.toList()));
                }
                default -> throw new IllegalArgumentException("type is undefined id=" + info.getId());
            }
            return collectItem;
        });
        if (item.getType() == 1 || Objects.isNull(aid)) {
            return item;
        }
        util.newSpan("get account:", span -> {
            InfoAccountListExample example = new InfoAccountListExample();
            example.createCriteria().andSourceEqualTo(ref).andIdEqualTo(id).andAidEqualTo(aid);
            List<InfoAccountList> infoAccountLists = accountListMapper.selectByExample(example);
            item.setValue(infoAccountLists.stream().map(InfoAccountList::getText).collect(Collectors.toList()));
        });
        return item;
    }

    private InfoItem getCollectItem(long id) {
        return getCollectItem(null, id, null);
    }

    @Override
    public List<AccountInfo> getAccountSaveInfo(List<Long> ids, String aid) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        AccountInfoExample example = new AccountInfoExample();
        example.createCriteria().andAidEqualTo(aid).andIdIn(ids);
        return accountInfoMapper.selectByExample(example);
    }

    @Override
    public Boolean submit(List<InfoAccountList> infos) {
        infos.stream().map(UpdateInfoAccountList::create).forEach(infoDao::insertOrUpdate);
        return true;
    }

    @Override
    public List<InfoItem> getExistInfo() {
        return infoDao.infosId()
                .stream()
                .map(this::getCollectItem)
                .collect(Collectors.toList());
    }

    public static class UpdateInfoAccountList extends InfoAccountList {
        private String newText;

        public static UpdateInfoAccountList create(InfoAccountList infoAccountList) {
            UpdateInfoAccountList update = new UpdateInfoAccountList();
            update.setNewText(infoAccountList.getText()).withText(infoAccountList.getText())
                    .withId(infoAccountList.getId())
                    .withAid(infoAccountList.getAid())
                    .withSource(infoAccountList.getSource());
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
