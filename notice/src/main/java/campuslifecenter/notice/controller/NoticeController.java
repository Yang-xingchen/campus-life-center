package campuslifecenter.notice.controller;

import brave.Tracer;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.model.Response;
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

@Api("通知")
@RestController
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
    private Tracer tracer;
    @Autowired
    private TracerUtil tracerUtil;

    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public Response<List<AccountNoticeInfo>> getNotice(@ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> {
            List<AccountNoticeInfo> noticeInfoList = tracerUtil.newSpan("account operation", span -> {
                String aid = cacheService.getAccountIdByToken(token);
                span.tag("account", aid);
                return noticeService.getAllNoticeOperationByAid(aid);
            });
            CountDownLatch countDownLatch = new CountDownLatch(noticeInfoList.size());
            noticeInfoList.forEach(noticeInfo -> tracerUtil.newSpanAsyn("notice: " + noticeInfo.getId(), span -> {
                noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
                if (noticeInfo.getTodoRef() == null) {
                    countDownLatch.countDown();
                    return;
                }
                Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, noticeInfo.getTodoRef());
                if (!r.isSuccess()) {
                    countDownLatch.countDown();
                    throw new RuntimeException("get todo fail: " + r.getMessage());
                }
                noticeInfo.setTodoList(r.getData());
                countDownLatch.countDown();
            }));
            try {
                if (!countDownLatch.await(1, TimeUnit.MINUTES)) {
                    throw new RuntimeException("get todo time out: " +
                            (noticeInfoList.size() - countDownLatch.getCount()) +
                            "/" + noticeInfoList.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return noticeInfoList;
        });
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public Response<AccountNoticeInfo> getNotice(@ApiParam("通知id") @PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        return Response.withData(() -> {
            AccountNoticeInfo notice = tracerUtil.newSpan("notice: " + id, span -> {
                return noticeService.getNoticeById(id);
            });
            if (!"".equals(token)) {
                tracerUtil.newSpan("account operation", span -> {
                    String aid = cacheService.getAccountIdByToken(token);
                    span.tag("account", aid);
                    noticeService.setNoticeAccountOperation(notice, aid);
                });
                if (notice.getTodoRef() == null) {
                    return notice;
                }
                tracerUtil.newSpan("todo", span -> {
                    Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, notice.getTodoRef());
                    if (!r.isSuccess()) {
                        throw new RuntimeException("get todo fail: " + r.getMessage());
                    }
                    notice.setTodoList(r.getData());
                });
                return notice;
            } else if (notice.getVisibility()) {
                throw new IllegalArgumentException("private notice");
            }
            return notice;
        });
    }

    @ApiOperation("更新")
    @PostMapping("/{id}/updateOperation")
    public Response<Boolean> updateOperation(@ApiParam("通知id") @PathVariable("id") long id,
                                             @ApiParam("token") @RequestParam(required = false, defaultValue = "") String token,
                                             @RequestBody AccountNotice accountNotice) {
        return Response.withData(() -> {
            if (id != accountNotice.getNid()) {
                throw new IllegalArgumentException("nid illegal: " + id + " != " + accountNotice.getNid());
            }
            String aid = cacheService.getAccountIdByToken(token);
            tracer.currentSpan().tag("account", aid);
            tracer.currentSpan().tag("nid", id + "");
            if (!Objects.equals(aid, accountNotice.getAid())) {
                throw new IllegalArgumentException("aid auth fail: " + aid + " != " + accountNotice.getAid());
            }
            System.out.println(accountNotice);
            return noticeService.updateAccountOperation(accountNotice);
        });
    }

    @ApiOperation("统计信息")
    @GetMapping("/{id}/analysis")
    public Response<NoticeAnalysis> analysis(@ApiParam("通知id") @PathVariable("id") long id,
                                             @ApiParam("token") @RequestParam(required = false, defaultValue = "") String token){
        return Response.withData(() -> {
            if ("".equals(token)) {
                throw new IllegalArgumentException("not token");
            }
            String aid = cacheService.getAccountIdByToken(token);
            tracer.currentSpan().tag("account", aid);
            tracer.currentSpan().tag("nid", id + "");
            AccountNoticeInfo notice = noticeService.getNoticeById(id);
            if (!Objects.equals(aid, notice.getCreator())) {
                throw new IllegalArgumentException("illegal account");
            }
            NoticeAnalysis analysis = tracerUtil.newSpan("get all account operation", span -> {
                return new NoticeAnalysis()
                        .setNid(id)
                        .setAccountNotice(noticeService.getAllAccountOperationByNid(id));
            });
            tracerUtil.newSpan("get publish account list", span -> {
                analysis.setPublishAccountList(
                        Stream.of(
                                Stream.of(new PublishAccount<>().setAccounts(
                                        analysis.getAccountNotice()
                                                .stream()
                                                .map(AccountNoticeKey::getAid)
                                                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                                                .collect(Collectors.toList())
                                )),
                                publishService.publicTodoStream(publishService.getPublishTodoByNid(id)),
                                publishService.publicInfoStream(publishService.getPublishInfoByNid(id)),
                                publishService.publicOrganizationStream(publishService.getPublishOrganizationByNid(id))
                        ).reduce(Stream::concat).get().collect(Collectors.toList())
                );
            });
            if (notice.getTodoRef() == null || "".equals(notice.getTodoRef())) {
                return analysis;
            }
            tracerUtil.newSpan("get all account todo operation", span -> {
                Response<List<AccountTodoInfo>> todo = todoService.getTodoBySource(notice.getTodoRef());
                if (!todo.isSuccess()) {
                    throw new RuntimeException("get todo fail: " + todo.getMessage());
                }
                analysis.setAccountTodos(todo.getData());
            });
            return analysis;
        });
    }

    @GetMapping("/todoRef")
    public Response<Long> getNoticeIdByTodoRef(@RequestParam String ref) {
        return Response.withData(() -> noticeService.getNoticeIdByTodoRef(ref));
    }

}
