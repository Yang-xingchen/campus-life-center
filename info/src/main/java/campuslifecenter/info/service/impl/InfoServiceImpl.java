package campuslifecenter.info.service.impl;

import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.*;
import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoCollect;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private InfoArrayMapper arrayMapper;
    @Autowired
    private InfoRadioMapper radioMapper;

    @Autowired
    private InfoListMapper listMapper;

    @Autowired
    private InfoListMapper infoListMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private CacheService cacheService;

    @Override
    public String addInfoCollect(AddInfoRequest addInfoRequest) {
        String uuid = UUID.randomUUID().toString();
        addInfoRequest.getInfos()
                .forEach(infoCollect -> insertCollect(infoCollect, addInfoRequest.getAids(), uuid));
        return uuid;
    }

    @SuppressWarnings("AlibabaSwitchStatement")
    private long insertCollect(AddInfoRequest.InfoCollect infoCollect, List<String> aids, String ref) {
        if (!infoCollect.isExist()) {
            Info info = infoCollect.toInfo();
            infoMapper.insert(info);
            infoCollect.setId(info.getId());
            switch (infoCollect.getType()) {
                case 0 -> textMapper.insert(infoCollect.toText());
                case 1 -> infoCollect
                        .getArrayInfo()
                        .stream()
                        .map(item -> {
                            long id = insertCollect(item, aids, ref);
                            return new InfoArray().withId(id).withPid(infoCollect.getId());
                        })
                        .forEach(arrayMapper::insert);
                case 2 -> infoCollect.getRadioInfo().forEach(s -> radioMapper.insert(
                        new InfoRadioKey().withId(infoCollect.getId()).withText(s)
                ));
                default -> throw new IllegalArgumentException("illegal info type.");
            }
        } else {
            Info info = infoMapper.selectByPrimaryKey(infoCollect.getId());
            infoCollect.setDefaultVisibility(info.getDefaultVisibility());
        }
        InfoList infoList = new InfoList().withListOrder(infoCollect.getOrder());
        infoList.withId(infoCollect.getId()).withSource(ref);
        infoListMapper.insert(infoList);
        aids.forEach(s -> {
            AccountInfo accountInfo = new AccountInfo()
                    .withVisibility(infoCollect.getDefaultVisibility());
            accountInfo.withAid(s).withId(infoList.getId());
            accountInfoMapper.insert(accountInfo);
        });
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
    public InfoCollect getAllAccountInfo(String ref) {
        InfoCollect collect = new InfoCollect();
        collect.setSource(ref);
        InfoListExample infoListExample = new InfoListExample();
        infoListExample.createCriteria().andSourceEqualTo(ref);
        collect.setItems(infoListMapper.selectByExample(infoListExample).stream().flatMap(infoList -> {
            AccountInfoExample example = new AccountInfoExample();
            example.createCriteria().andIdEqualTo(infoList.getId());
            return accountInfoMapper.selectByExample(example).stream().map(accountInfo -> {
                String aid = accountInfo.getAid();
                InfoCollect.InfoCollectItem item = getCollectItem(infoList.getId(), aid);
                item.setOrder(infoList.getListOrder());
                item.setAid(aid);
                item.setAccountName(cacheService.getAccountNameByID(aid));
                return item;
            });
        }).collect(Collectors.toList()));
        return collect;
    }

    @Override
    public InfoCollect getAccountInfo(String ref, String aid) {
        InfoCollect collect = new InfoCollect();
        collect.setSource(ref);
        InfoListExample infoListExample = new InfoListExample();
        infoListExample.createCriteria().andSourceEqualTo(ref);
        collect.setItems(infoListMapper.selectByExample(infoListExample).stream().map(infoList -> {
            InfoCollect.InfoCollectItem item = getCollectItem(infoList.getId(), aid);
            item.setOrder(infoList.getListOrder());
            return item;
        }).collect(Collectors.toList()));
        return collect;
    }

    @SuppressWarnings("AlibabaSwitchStatement")
    private InfoCollect.InfoCollectItem getCollectItem(long id, String aid) {
        Info info = infoMapper.selectByPrimaryKey(id);
        InfoCollect.InfoCollectItem item = InfoCollect.InfoCollectItem.create(info);
        switch (info.getType()) {
            case 0 -> ((InfoCollect.TextCollectItem) item)
                    .setSample(textMapper.selectByPrimaryKey(id).getSample());
            case 1 -> {
                InfoCollect.ArrayCollectItem aItem = (InfoCollect.ArrayCollectItem) item;
                InfoArrayExample arrayExample = new InfoArrayExample();
                arrayExample.createCriteria().andPidEqualTo(id);
                aItem.setItems(arrayMapper.selectByExample(arrayExample)
                        .stream().map(InfoArray::getId).map(i->getCollectItem(i, aid))
                        .collect(Collectors.toList()));
            }
            case 2 -> {
                InfoCollect.RadioCollectItem rItem = (InfoCollect.RadioCollectItem) item;
                InfoRadioExample radioExample = new InfoRadioExample();
                radioExample.createCriteria().andIdEqualTo(id);
                rItem.setRadio(radioMapper.selectByExample(radioExample)
                        .stream().map(InfoRadioKey::getText).collect(Collectors.toList()));
            }
            default -> throw new IllegalArgumentException("type is undefined id=" + info.getId());
        }
        if (item.getType() == 1) {
            return item;
        }
        AccountInfoKey accountInfo = new AccountInfoKey();
        accountInfo.withId(item.getId()).withAid(aid);
        item.setValue(accountInfoMapper.selectByPrimaryKey(accountInfo).getText());
        return item;
    }
}
