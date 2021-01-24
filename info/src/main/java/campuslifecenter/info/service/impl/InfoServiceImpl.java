package campuslifecenter.info.service.impl;

import campuslifecenter.info.component.Util;
import campuslifecenter.info.dao.InfoDao;
import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.*;
import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
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
    private AccountSubmitMapper submitMapper;
    @Autowired
    private InfoDao infoDao;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private Util util;

    private static final String[] TYPE_MAP = new String[]{"文本", "组合", "单选"};

    @Override
    public String addInfoCollect(AddInfoRequest addInfoRequest) {
        String uuid = UUID.randomUUID().toString();
        long id = insertCollect(addInfoRequest.getInfo(), addInfoRequest.getAids(), uuid);
        addInfoRequest.getAids().forEach(s -> util.newSpan("init account: " + s, span -> {
            AccountSubmit accountSubmit = new AccountSubmit();
            accountSubmit.withId(id).withRef(uuid).withAid(s);
            submitMapper.insert(accountSubmit);
        }));
        return uuid + ":" + id;
    }

    private long insertCollect(AddInfoRequest.InfoCollect infoCollect, List<String> aids, String ref) {
        if (!infoCollect.isExist()) {
            util.newSpan("insert not exist info.", span -> {
                span.tag("type", TYPE_MAP[infoCollect.getType()]);
                Info info = infoCollect.toInfo();
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
        return infoCollect.getId();
    }

    @Override
    public InfoItem getCollectItem(long id, Consumer<InfoItem> consumer) {
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
                            .stream().map(InfoComposite::getId).map(i -> getCollectItem(i, consumer))
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
        if (consumer != null) {
            consumer.accept(item);
        }
        return item;
    }

    private InfoItem getCollectItem(long id) {
        return getCollectItem(id, null);
    }

    @Override
    public List<InfoItem> getExistInfo() {
        return infoDao.infosId()
                .stream()
                .map(this::getCollectItem)
                .collect(Collectors.toList());
    }
}
