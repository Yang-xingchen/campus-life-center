package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.AccountNoticeMapper;
import campuslifecenter.notice.mapper.AccountSubscribeMapper;
import campuslifecenter.notice.mapper.NoticeMapper;
import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.NoticeInfo;
import campuslifecenter.notice.model.Organization;
import campuslifecenter.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AccountSubscribeMapper accountSubscribeMapper;
    @Autowired
    private AccountNoticeMapper accountNoticeMapper;

    public static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            PROCESSORS, PROCESSORS * 2,
            10, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(128),
            new ThreadPoolExecutor.AbortPolicy());

    @Override
    public List<NoticeInfo> getNoticeByAid(AccountInfo accountInfo){
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Future<List<NoticeInfo>> organizationNotices = threadPoolExecutor
                .submit(() -> getTask(accountInfo.getOrganizations(), this::getByOrganization, countDownLatch));
        Future<List<NoticeInfo>> subscribeNotice = threadPoolExecutor
                .submit(() -> getTask(accountInfo.getSignId(), this::getBySubscribe, countDownLatch));
        Future<List<NoticeInfo>> accountNotice = threadPoolExecutor
                .submit(() -> getTask(accountInfo.getSignId(), this::getByAccount, countDownLatch));
        try {
            countDownLatch.await();
            List<NoticeInfo> noticeInfoList = new ArrayList<>(
                    organizationNotices.get().size() +
                            subscribeNotice.get().size() +
                            accountNotice.get().size());
            noticeInfoList.addAll(organizationNotices.get());
            noticeInfoList.addAll(subscribeNotice.get());
            noticeInfoList.addAll(accountNotice.get());
            return noticeInfoList;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<NoticeInfo> getTask(T arg, Function<T, List<NoticeInfo>> function, CountDownLatch countDownLatch) {
        List<NoticeInfo> noticeInfoList = function.apply(arg);
        countDownLatch.countDown();
        return noticeInfoList;
    }

    private List<NoticeInfo> getByOrganization(List<Organization> organizations) {
        NoticeExample example = new NoticeExample();
        example.createCriteria()
                .andOrganizationIn(organizations
                        .stream()
                        .map(Organization::getId)
                        .collect(Collectors.toList()))
                .andPublicTypeEqualTo((byte) 0);
        return noticeMapper.selectByExample(example)
                .stream()
                .map(NoticeInfo::withNotice)
                .collect(Collectors.toList());
    }

    private List<NoticeInfo> getBySubscribe(String aid) {
        AccountSubscribeExample example = new AccountSubscribeExample();
        example.createCriteria().andAidEqualTo(aid);
        List<Integer> oids = accountSubscribeMapper
                .selectByExample(example)
                .stream()
                .map(AccountSubscribeKey::getOid)
                .collect(Collectors.toList());
        NoticeExample noticeExample = new NoticeExample();
        noticeExample.createCriteria().andPublicTypeEqualTo((byte) 1).andOrganizationIn(oids);
        return noticeMapper.selectByExample(noticeExample)
                .stream()
                .map(NoticeInfo::withNotice)
                .collect(Collectors.toList());
    }

    private List<NoticeInfo> getByAccount(String aid) {
        AccountNoticeExample example = new AccountNoticeExample();
        example.createCriteria().andAidEqualTo(aid);
        List<Integer> nids = accountNoticeMapper
                .selectByExample(example)
                .stream()
                .map(AccountNotice::getNid)
                .collect(Collectors.toList());
        NoticeExample noticeExample = new NoticeExample();
        noticeExample.createCriteria().andPublicTypeEqualTo((byte)2).andIdIn(nids);
        return noticeMapper.selectByExample(noticeExample)
                .stream()
                .map(NoticeInfo::withNotice)
                .collect(Collectors.toList());
    }
}
