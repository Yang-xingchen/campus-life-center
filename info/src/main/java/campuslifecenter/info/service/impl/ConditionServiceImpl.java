package campuslifecenter.info.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.ConditionAccountUpdate;
import campuslifecenter.info.component.InfoStream;
import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSaveInfoExample;
import campuslifecenter.info.entry.ConditionInfo;
import campuslifecenter.info.entry.ConditionInfoExample;
import campuslifecenter.info.mapper.AccountSaveInfoMapper;
import campuslifecenter.info.mapper.ConditionInfoMapper;
import campuslifecenter.info.service.ConditionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ConditionServiceImpl implements ConditionService {


    @Autowired
    @Qualifier(InfoStream.CONDITION_CHANGE)
    private MessageChannel conditionChannel;


    @Autowired
    private AccountSaveInfoMapper accountSaveMapper;
    @Autowired
    private ConditionInfoMapper conditionMapper;

    @Autowired
    private TracerUtil tracerUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${info.redis.condition}")
    private String CONDITION_PREFIX;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @NewSpan("condition change")
    public void update(AccountSaveInfo saveInfo) {
        ConditionAccountUpdate update = new ConditionAccountUpdate();
        update.setAid(saveInfo.getAid());
        ConditionInfoExample example = new ConditionInfoExample();
        example.createCriteria().andDynamicEqualTo(true).andIidEqualTo(saveInfo.getId());
        List<String> refs = conditionMapper.selectByExample(example).stream()
                .filter(condition -> filter(condition.getType(), condition.getText(), saveInfo.getContent()))
                .map(ConditionInfo::getRef).collect(Collectors.toList());
        if (refs.isEmpty()) {
            return;
        }
        tracerUtil.getSpan().tag("refs", refs.toString());
        update.setRefs(refs);
        conditionChannel.send(MessageBuilder.withPayload(update).build());
    }

    @Override
    @NewSpan("publish")
    public boolean publish(@SpanTag("ref") String ref) {
        BoundValueOperations<String, String> conditionOps = redisTemplate.boundValueOps(CONDITION_PREFIX + ref);
        try {
            ConditionInfo info = objectMapper.readValue(conditionOps.get(), ConditionInfo.class);
            conditionMapper.insert(info);
            redisTemplate.delete(CONDITION_PREFIX + ref);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static final int ADD_START = 0x1;
    private static final int ADD_END = 0x2;

    private static final int NOT = 0x8;

    private static final int NUMBER = 0x10;
    private static final int GREATER = 0x1;
    private static final int LESS = 0x2;
    private static final int BETWEEN = 0x4;

    private boolean filter(int type, String text, String content) {
        if ((type & NUMBER) != 0) {
            try {
                double number = Double.parseDouble(content);
                if ((type & BETWEEN) != 0) {
                    String[] texts = text.split(" ");
                    return Double.parseDouble(texts[0]) < number && number < Double.parseDouble(texts[1]);
                }
                return numberCompare(type, Double.parseDouble(text), Double.parseDouble(content));
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            }
        }
        boolean reverse = (type & NOT) != 0;
        if ((type & ADD_START) != 0 && content.endsWith(text) == reverse) {
            return false;
        }
        if ((type & ADD_END) != 0 && content.startsWith(text) == reverse) {
            return false;
        }
        return true;
    }

    @Override
    @NewSpan("get accounts")
    public List<String> getAccounts(@SpanTag("ref") String ref) {
        BoundValueOperations<String, String> conditionOps = redisTemplate.boundValueOps(CONDITION_PREFIX + ref);
        ConditionInfo info = null;
        try {
            String cache = conditionOps.get();
            if (cache != null) {
                info = objectMapper.readValue(cache, ConditionInfo.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (info == null) {
            info = conditionMapper.selectByPrimaryKey(ref);
        }
        return getAccounts(info);
    }

    @Override
    public List<String> getAccounts(ConditionInfo info) {
        if (info == null) {
            return List.of();
        }
        return select(info.getIid(), info.getType(), info.getText());
    }

    @Override
    public String create(ConditionInfo conditionInfo) {
        String uuid = UUID.randomUUID().toString();
        conditionInfo.setRef(uuid);
        String cache;
        try {
            cache = objectMapper.writeValueAsString(conditionInfo);
        } catch (JsonProcessingException e) {
            throw new ResponseException(e);
        }
        redisTemplate.opsForValue().set(CONDITION_PREFIX + uuid, cache, 1, TimeUnit.DAYS);
        return uuid;
    }

    @Override
    @NewSpan("select")
    public List<String> select(@SpanTag("id") long id, @SpanTag("type") int type, @SpanTag("text") String text) {
        if ((type & NUMBER) != 0) {
            if ((type & BETWEEN) != 0) {
                numberBetween(id, text);
            }
            return numberCompare(id, type, text);
        }
        return text(id, type, text);
    }

    private List<String> text(long id, int type, String text) {
        StringBuilder like = new StringBuilder();
        if ((type & ADD_START) != 0) {
            like.append('%');
        }
        like.append(text);
        if ((type & ADD_END) != 0) {
            like.append('%');
        }
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        if ((type & NOT) != 0) {
            example.createCriteria().andIdEqualTo(id).andContentNotLike(like.toString());
        } else {
            example.createCriteria().andIdEqualTo(id).andContentLike(like.toString());
        }
        return accountSaveMapper
                .selectByExample(example)
                .stream()
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    private List<String> numberBetween(long id, String text) {
        String[] texts = text.split(" ");
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andIdEqualTo(id).andContentBetween(texts[0], texts[1]);
        return accountSaveMapper
                .selectByExample(example)
                .stream()
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    private List<String> numberCompare(long id, int type, String text) {
        AccountSaveInfoExample example = new AccountSaveInfoExample();
        example.createCriteria().andIdEqualTo(id);
        double d = Double.parseDouble(text);
        return accountSaveMapper
                .selectByExample(example)
                .stream()
                .filter(accountSaveInfo -> {
                    try {
                        return numberCompare(type, d, Double.parseDouble(accountSaveInfo.getContent()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .map(AccountSaveInfo::getAid)
                .collect(Collectors.toList());
    }

    private boolean numberCompare(int type, double condition, double content) {
        boolean ret;
        if ((type & GREATER) != 0) {
            ret = Double.compare(content, condition) > 0;
        } else if ((type & LESS) != 0) {
            ret = Double.compare(content, condition) < 0;
        } else {
            ret = Double.compare(content, condition) == 0;
        }
        // 判断及反转
        return ((type & NOT) == 0) == ret;
    }

}
