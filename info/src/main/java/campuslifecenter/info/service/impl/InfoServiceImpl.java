package campuslifecenter.info.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.info.dao.InfoDao;
import campuslifecenter.info.entry.*;
import campuslifecenter.info.mapper.*;
import campuslifecenter.info.model.InfoCollectRequest;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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
    private RefRootMapper refRootMapper;
    @Autowired
    private InfoDao infoDao;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    @Value("${info.redis.cache.info}")
    private String INFO_PREFIX;
    @Value("${info.redis.cache.ref-info}")
    private String REF_PREFIX;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String[] TYPE_MAP = new String[]{"文本", "组合", "单选"};

    @Override
    @NewSpan("add collect")
    public String addCollect(InfoCollectRequest.AddInfoRequest addInfoRequest) {
        String uuid = UUID.randomUUID().toString();
        addInfoRequest.setExist(false);
        long id = insertCollect(addInfoRequest);
        refRootMapper.insert(new RefRoot().withRef(uuid).withRoot(id));
        addInfoRequest.getAids().stream().distinct()
                .forEach(s -> tracerUtil.newSpan("init account: " + s, span -> {
                    AccountSubmit accountSubmit = new AccountSubmit();
                    accountSubmit.withId(id).withRoot(id).withAid(s).withMultipleIndex(0);
                    submitMapper.insertSelective(accountSubmit);
                }));
        return uuid;
    }

    private long insertCollect(InfoCollectRequest infoCollect) {
        if (!infoCollect.isExist()) {
            tracerUtil.newSpan("insert not exist info.", span -> {
                span.tag("type", TYPE_MAP[infoCollect.getType()]);
                span.tag("name", infoCollect.getName());
                // insert info
                Info info = infoCollect.toInfo();
                infoMapper.insertSelective(info);
                // insert info type
                infoCollect.setId(info.getId());
                switch (infoCollect.getType()) {
                    case 0 -> textMapper.insertSelective(new InfoText()
                            .withId(infoCollect.getId())
                            .withType(infoCollect.getTextType())
                            .withSample(infoCollect.getSample())
                            .withRegular(infoCollect.getRegular()));
                    case 1 -> infoCollect
                            .getCompositeInfo()
                            .stream()
                            .map(item -> {
                                long id = insertCollect(item);
                                return (InfoComposite) new InfoComposite().withCompositeIndex(infoCollect.getOrder())
                                        .withId(id).withPid(infoCollect.getId());
                            })
                            .forEach(compositeMapper::insertSelective);
                    case 2 -> infoCollect.getRadioInfo()
                            .forEach(s -> radioMapper.insertSelective(
                                    new InfoRadioKey().withId(infoCollect.getId()).withText(s)));
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
    public List<InfoItem> getExistInfo() {
        return infoDao.infosId()
                .stream()
                .map(this::getCollectItem)
                .collect(Collectors.toList());
    }

    @Override
    public long getRoot(String ref) {
        BoundValueOperations<String, String> refOps = redisTemplate.boundValueOps(REF_PREFIX + ref);
        String cache = refOps.get();
        if (cache != null) {
            return Long.parseLong(cache);
        } else {
            long root = refRootMapper.selectByPrimaryKey(ref).getRoot();
            refOps.set("" + root);
            return root;
        }
    }

    private InfoItem getCollectItem(long id) {
        return getInfoItem(id, null);
    }

    @Override
    public InfoItem getInfoItem(long id, Consumer<InfoItem> consumer) {
        InfoItem item = null;
        BoundValueOperations<String, String> infoOps = redisTemplate.boundValueOps(INFO_PREFIX + id);
        String cache = infoOps.get();
        if (cache != null) {
            try {
                item = objectMapper.readValue(cache, InfoItem.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if (item == null ) {
            item = tracerUtil.newSpan("get info: " + id, span -> {
                Info info = infoMapper.selectByPrimaryKey(id);
                span.tag("type", TYPE_MAP[info.getType()]);
                span.tag("name", info.getName());
                InfoItem collectItem = InfoItem.create(info);
                switch (info.getType()) {
                    case 0 -> {
                        InfoText tItem = textMapper.selectByPrimaryKey(id);
                        ((InfoItem.TextItem) collectItem)
                                .setSample(tItem.getSample())
                                .setRegular(tItem.getRegular())
                                .setTextType(tItem.getType());
                    }
                    case 1 -> {
                        InfoItem.CompositeItem aItem = (InfoItem.CompositeItem) collectItem;
                        InfoCompositeExample arrayExample = new InfoCompositeExample();
                        arrayExample.createCriteria().andPidEqualTo(id);
                        aItem.setItems(compositeMapper.selectByExample(arrayExample)
                                .stream().map(InfoComposite::getId).map(i -> getInfoItem(i, consumer))
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
            InfoItem info = item.toInfo();
            tracerUtil.newSpan("write cache", span -> {
                try {
                    infoOps.set(objectMapper.writeValueAsString(info), 1, TimeUnit.DAYS);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        if (consumer != null) {
            consumer.accept(item);
        }
        return item;
    }
}
