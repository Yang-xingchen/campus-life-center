package campuslifecenter.notice.service.impl;


import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.IdName;
import campuslifecenter.notice.entry.NoticeCondition;
import campuslifecenter.notice.entry.NoticeConditionExample;
import campuslifecenter.notice.mapper.NoticeConditionMapper;
import campuslifecenter.notice.model.PublishAccounts;
import campuslifecenter.notice.model.PublishAccountsConfig;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.PublishAccountService;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PublishAccountServiceImpl implements PublishAccountService {


    @Autowired
    private PublishAccountsConfig config;

    @Autowired
    private NoticeConditionMapper conditionMapper;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<PublishAccounts> getPublishByNid(long id, boolean showName) {
        NoticeConditionExample example = new NoticeConditionExample();
        example.createCriteria().andNidEqualTo(id);
        return conditionMapper
                .selectByExample(example)
                .stream()
                .map(noticeCondition -> {
                    PublishAccounts publishAccounts = publishAccounts(noticeCondition, showName);
                    publishAccounts.setNoticeCondition(noticeCondition);
                    return publishAccounts;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Stream<PublishAccounts> publishAccountsStream(List<NoticeCondition> publishConditions, boolean showName) {
        return publishConditions.stream().map(noticeCondition -> publishAccounts(noticeCondition, showName));
    }

    @Override
    @NewSpan("get accounts")
    public PublishAccounts publishAccounts(NoticeCondition condition, boolean showName) {
        if (condition.getType() == null) {
            condition = conditionMapper.selectByPrimaryKey(condition.getRef());
            if (condition == null) {
                return null;
            }
        }
        String url = String.format(config.getAccountsMap().get(condition.getType().toString()), condition.getRef());
        tracerUtil.getSpan().tag("url", url);
        List<String> aids;
        try (CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            try (InputStream content = response.getEntity().getContent()) {
                JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, String.class);
                aids = objectMapper.readValue(content, type);
            }
        } catch (IOException e) {
            throw new ResponseException("read result fail", e);
        }
        tracerUtil.getSpan().tag("aids", aids.toString());
        List<IdName<String>> idNames = aids.stream().map(s -> {
            IdName<String> idName = new IdName<>();
            idName.setId(s);
            if (showName) {
                idName.setName(cacheService.getAccountNameByID(s));
            }
            return idName;
        }).collect(Collectors.toList());
        return new PublishAccounts().setNoticeCondition(condition).setAccounts(idNames);
    }

    @Override
    @NewSpan("publish")
    public void publish(NoticeCondition condition) {
        String url = String.format(config.getPublishMap().get(condition.getType().toString()), condition.getRef());
        tracerUtil.getSpan().tag("url", url);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            String s = new String(response.getEntity().getContent().readAllBytes());
            tracerUtil.getSpan().tag("res", s);
        } catch (IOException e) {
            throw new ResponseException("read result fail");
        }
    }
}
