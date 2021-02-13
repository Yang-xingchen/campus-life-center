package campuslifecenter.notice.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.AccountNoticeKey;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private PublishService publishService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private TracerUtil tracerUtil;

    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public List<AccountNoticeInfo> getNotice(@ApiParam("token") @RequestParam String token) {
        List<AccountNoticeInfo> noticeInfoList = tracerUtil.newSpan("account operation", span -> {
            String aid = cacheService.getAccountIdByToken(token);
            span.tag("account", aid);
            return noticeService.getAllNoticeOperationByAid(aid);
        });
        CountDownLatch countDownLatch = new CountDownLatch(noticeInfoList.size());
        noticeInfoList.forEach(noticeInfo -> tracerUtil.newSpanAsyn("notice: " + noticeInfo.getId(), span -> {
            try {
                noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
                if (noticeInfo.getRef() == null) {
                    return;
                }
                Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, noticeInfo.getRef());
                noticeInfo.setTodoList(r.checkGet(TODO, "get todo fail"));
            } finally {
                countDownLatch.countDown();
            }
        }));
        try {
            if (!countDownLatch.await(3, TimeUnit.MINUTES)) {
                throw new ResponseException(
                        String.format("get todo time out: %d/%d",
                                noticeInfoList.size() - countDownLatch.getCount(), noticeInfoList.size()),
                        5200);
            }
        } catch (InterruptedException e) {
            throw new ResponseException(e);
        }
        return noticeInfoList;
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public AccountNoticeInfo getNotice(@ApiParam("通知id") @PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        String aid =  cacheService.getAccountIdByToken(token);
        AccountNoticeInfo notice = tracerUtil.newSpan("notice: " + id, span -> {
            return noticeService.getNoticeById(id);
        });
        if (notice.getVisibility() == NoticeConst.VISIBILITY_PRIVATE) {
            AuthException.checkThrow(notice.getCreator(), aid);
        }
        AccountNotice accountOperation = tracerUtil.newSpan("account operation", span -> {
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
            Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, notice.getRef());
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
        AuthException.checkThrow(aid, notice.getCreator());
        NoticeAnalysis analysis = tracerUtil.newSpan("get all account operation", span -> {
            return new NoticeAnalysis()
                    .setNid(id)
                    .setAccountNotice(noticeService.getAllAccountOperationByNid(id));
        });
        tracerUtil.newSpan("get publish account list", span -> {
            analysis.setPublishAccountList(
                    Stream.concat(
                            Stream.of(new PublishAccount<>().setAccounts(
                                    analysis.getAccountNotice()
                                            .stream()
                                            .map(AccountNoticeKey::getAid)
                                            .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                                            .collect(Collectors.toList())
                            )),
                            publishService.getPublishByNid(id).stream()
                    ).collect(Collectors.toList())
            );
        });
        return analysis;
    }

    @GetMapping("/todoRef")
    public Long getNoticeIdByTodoRef(@RequestParam String ref) {
        return noticeService.getNoticeIdByRef(ref);
    }


}
