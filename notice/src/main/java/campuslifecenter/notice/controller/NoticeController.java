package campuslifecenter.notice.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.LazyList;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.NoticeAnalysis;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static campuslifecenter.common.exception.ProcessException.TODO;
import static campuslifecenter.notice.model.NoticeConst.VISIBILITY_PRIVATE;
import static campuslifecenter.notice.model.NoticeConst.VISIBILITY_PUBLIC;

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
     * score = min(id, 0x00ff_ffff_ffff_ffff) + 修正
     *
     * <p>修正项目:</p>
     * <li>删除 - Long.MAX_VALUE </li>
     * <li>置顶 + 0x4000_0000_0000_0000 </li>
     * <li>未读 + 0x2000_0000_0000_0000 </li>
     * <li>加入发布的组织 + 0x1000_0000_0000_0000 </li>
     * <li>关注发布的组织 + 0x0800_0000_0000_0000 </li>
     * <li>重要度 + importance << 54,<br/>
     *    即 [0x0100_0000_0000_0000, 0x0500_0000_0000_0000]</li>
     * @param item 算分对象
     * @param belong 账户加入的组织
     * @param subscribe 账户关注的组织
     * @return 得分, 值越大优先级越高
     */
    private long getScore(AccountNoticeInfo item, List<Integer> belong, List<Integer> subscribe) {
        long score = Math.min(0x00ff_ffff_ffff_ffffL, item.getId());
        long importance = item.getImportance() + item.getRelativeImportance();
        importance = Math.min(importance, 5L);
        importance = Math.max(importance, 1L);
        score |= importance << 54;
        score |= item.getTop() ? 0x4000_0000_0000_0000L : 0L;
        score |= item.getLooked() ? 0L : 0x2000_0000_0000_0000L;
        score |= belong.contains(item.getOrganization()) ? 0x1000_0000_0000_0000L : 0L;
        score |= subscribe.contains(item.getOrganization()) ? 0x0800_0000_0000_0000L : 0L;
        score -= item.getDel() ? Long.MAX_VALUE : 0;
        return score;
    }


    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public Response<LazyList<AccountNoticeInfo>> getNotice(@ApiParam("token") @RequestParam String token,
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
                int p = 0;
                try {
                    p = Math.max(Integer.parseInt(page.trim()), 0);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                AccountService.AccountInfo accountInfo = cacheService.getAccountInfo(aid);
                List<Integer> belong = accountInfo.getOrganizations()
                        .stream()
                        .map(AccountService.AccountInfo.OrganizationInfo::getId)
                        .collect(Collectors.toList());
                List<Integer> subscribe = subscribeService.getSubscribeList(accountInfo.getId());
                return LazyList.withData(noticeInfoList, p, pageSize, value -> getScore(value, belong, subscribe));
            });
        } else {
            lazyList = new LazyList<>();
            lazyList.setItems(noticeInfoList)
                    .setPage(0)
                    .setTotal(noticeInfoList.size())
                    .setSize(pageSize);
        }
        CountDownLatch countDownLatch = new CountDownLatch(lazyList.size());
        AtomicInteger error = new AtomicInteger(0);
        lazyList.forEach(noticeInfo -> tracerUtil.newSpanAsync("notice: " + noticeInfo.getId(), countDownLatch, span -> {
            noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
            if (noticeInfo.getRef() == null) {
                return;
            }
            try {
                Response<List<TodoService.AccountTodoInfo>> r = todoService.getTodoByTokenAndRef(token, noticeInfo.getRef());
                noticeInfo.setTodoList(r.checkGet(TODO, "get todo fail"));
            } catch (RuntimeException e) {
                span.error(e);
                error.incrementAndGet();
            }
        }));
        try {
            if (!countDownLatch.await(30, TimeUnit.SECONDS) || error.get() != 0) {
                tracerUtil.getSpan().tag("fail", error.get() + "/" + lazyList.size());
                return Response.withData(lazyList).setCode(101).setMessage("服务繁忙，部分数据加载失败，请稍后再试");
            }
        } catch (InterruptedException e) {
            throw new ResponseException(e);
        }
        return Response.withData(lazyList);
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public Response<AccountNoticeInfo> getNotice(@ApiParam("通知id") @PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        if (token == null || "".equals(token)) {
            token = "null";
        }
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("account", aid + "");
        AccountNoticeInfo notice = tracerUtil.newSpan("notice: " + id, span -> {
            return noticeService.getNoticeById(id);
        });
        if (notice == null) {
            return null;
        }
        if (notice.getVisibility() == VISIBILITY_PRIVATE) {
            AuthException.checkThrow(notice.getCreator(), aid);
        }
        AccountNotice accountOperation = tracerUtil.newSpan("account operation", span -> {
            if (aid == null) {
                return null;
            }
            return noticeService.getNoticeAccountOperation(id, aid);
        });
        if (notice.getVisibility() != VISIBILITY_PUBLIC && accountOperation == null) {
            throw new AuthException();
        }
        notice.setAccountOperation(accountOperation);
        if (notice.getRef() == null) {
            return Response.withData(notice);
        }
        String finalToken = token;
        try {
            tracerUtil.newSpan("todo", span -> {
                Response<List<TodoService.AccountTodoInfo>> r = todoService.getTodoByTokenAndRef(finalToken, notice.getRef());
                notice.setTodoList(r.checkGet(TODO, "get todo fail"));
            });
        } catch (RuntimeException e) {
            return Response.withData(notice).setCode(101).setMessage("服务繁忙，部分数据加载失败，请稍后再试");
        }
        return Response.withData(notice);
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
