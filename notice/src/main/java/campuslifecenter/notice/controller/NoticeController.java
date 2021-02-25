package campuslifecenter.notice.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.LazyList;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

import static campuslifecenter.common.exception.ProcessException.TODO;

@Api("通知")
@RestWarpController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private PublishAccountService publishAccountService;
    @Autowired
    private OrganizationSubscribeService subscribeService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    @Value("${notice.page-size}")
    private int pageSize;

    /**
     * 算分
     * score = id + 重要度 * 权重 + 修正
     *
     * <p>修正项目:</p>
     * <li>删除 - Long.MAX_VALUE</li>
     * <li>置顶 + (Long.MAX_VALUE + 1) / 2</li>
     * <li>未读 + (Long.MAX_VALUE + 1) / 4</li>
     * <li>加入发布的组织 + (Long.MAX_VALUE + 1) / 8</li>
     * <li>关注发布的组织 + (Long.MAX_VALUE + 1) / 16</li>
     * @param item 算分对象
     * @param weight 重要度权重
     * @return 得分, 值越大优先级越高
     */
    private long getScore(AccountNoticeInfo item, long weight, List<Integer> belong, List<Integer> subscribe) {
        long score = item.getId() + item.getImportance() * weight;
        score = Math.min(0x07ff_ffff_ffff_ffffL, score);
        score |= item.getTop() ? 0x4000_0000_0000_0000L : 0L;
        score |= item.getLooked() ? 0L : 0x2000_0000_0000_0000L;
        score |= belong.contains(item.getOrganization()) ? 0x1000_0000_0000_0000L : 0;
        score |= subscribe.contains(item.getOrganization()) ? 0x0800_0000_0000_0000L : 0;
        score -= item.getDel() ? Long.MAX_VALUE : 0;
        return score;
    }


    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public LazyList<AccountNoticeInfo> getNotice(@ApiParam("token") @RequestParam String token,
                                             @RequestParam(required = false, defaultValue = "") String page) {
        String aid = cacheService.getAccountIdByToken(token);
        List<AccountNoticeInfo> noticeInfoList = tracerUtil.newSpan("account operation", span -> {
            span.tag("account", aid);
            return noticeService.getAllNoticeOperationByAid(aid);
        });
        tracerUtil.getSpan().tag("page", page);
        LazyList<AccountNoticeInfo> lazyList;
        if (pageSize > 0 && noticeInfoList.size() > pageSize) {
            lazyList = tracerUtil.newSpan("lazy", scopedSpan -> {
                long weight = noticeInfoList.stream().mapToLong(Notice::getId).max().orElse(0) / 5;
                int p = 0;
                try {
                    p = Math.max(Integer.parseInt(page), 0);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                AccountService.AccountInfo accountInfo = cacheService.getAccountInfo(aid);
                List<Integer> belong = accountInfo.getOrganizations()
                        .stream()
                        .map(AccountService.AccountInfo.OrganizationInfo::getId)
                        .collect(Collectors.toList());
                List<Integer> subscribe = subscribeService.getSubscribeList(accountInfo.getId());
                return LazyList.withData(noticeInfoList, p, pageSize, value -> getScore(value, weight, belong, subscribe));
            });
        } else {
            lazyList = new LazyList<>();
            lazyList.setItems(noticeInfoList)
                    .setPage(0)
                    .setTotal(noticeInfoList.size())
                    .setSize(pageSize);
        }
        CountDownLatch countDownLatch = new CountDownLatch(lazyList.size());
        lazyList.forEach(noticeInfo -> tracerUtil.newSpanAsync("notice: " + noticeInfo.getId(), countDownLatch, span -> {
            noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
            if (noticeInfo.getRef() == null) {
                return;
            }
            Response<List<TodoService.AccountTodoInfo>> r = todoService.getTodoByTokenAndRef(token, noticeInfo.getRef());
            noticeInfo.setTodoList(r.checkGet(TODO, "get todo fail"));
        }));
        try {
            if (!countDownLatch.await(3, TimeUnit.MINUTES)) {
                long complete = lazyList.size() - countDownLatch.getCount();
                throw new ResponseException(
                        String.format("get todo time out: %d/%d", complete, lazyList.size()),
                        5200);
            }
        } catch (InterruptedException e) {
            throw new ResponseException(e);
        }
        return lazyList;
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public AccountNoticeInfo getNotice(@ApiParam("通知id") @PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        String aid =  cacheService.getAccountIdByToken(token);
        AccountNoticeInfo notice = tracerUtil.newSpan("notice: " + id, span -> {
            return noticeService.getNoticeById(id);
        });
        if (notice == null) {
            return null;
        }
        if (notice.getVisibility() == NoticeConst.VISIBILITY_PRIVATE) {
            AuthException.checkThrow(notice.getCreator(), aid);
        }
        AccountNotice accountOperation = tracerUtil.newSpan("account operation", span -> {
            if (aid == null) {
                return null;
            }
            return noticeService.getNoticeAccountOperation(id, aid);
        });
        if (notice.getVisibility() == NoticeConst.VISIBILITY_ACCOUNT && accountOperation == null) {
            throw new AuthException();
        }
        notice.setAccountOperation(accountOperation);
        if (notice.getRef() == null) {
            return notice;
        }
        tracerUtil.newSpan("todo", span -> {
            Response<List<TodoService.AccountTodoInfo>> r = todoService.getTodoByTokenAndRef(token, notice.getRef());
            notice.setTodoList(r.checkGet(TODO, "get todo fail"));
        });
        return notice;
    }

    @ApiOperation("统计信息")
    @GetMapping("/{id}/accountAnalysis")
    public NoticeAnalysis analysis(@ApiParam("通知id") @PathVariable("id") long id,
                                             @ApiParam("token") @RequestParam String token){
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("account", aid);
        tracerUtil.getSpan().tag("nid", id + "");
        AccountNoticeInfo notice = noticeService.getNoticeById(id);
        if (notice == null) {
            return null;
        }
        AuthException.checkThrow(aid, notice.getCreator());
        NoticeAnalysis analysis = tracerUtil.newSpan("get all account operation", span -> {
            return new NoticeAnalysis()
                    .setNid(id)
                    .setAccountNotice(noticeService.getAllAccountOperationByNid(id));
        });
        tracerUtil.newSpan("get publish account list", span -> {
            analysis.setPublishAccountsList(publishAccountService.getPublishByNid(id, true));
        });
        return analysis;
    }

}
