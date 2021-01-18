package campuslifecenter.notice.controller;

import brave.Tracer;
import brave.propagation.CurrentTraceContext;
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


    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static ExecutorService threadPoolExecutor;
    static {
        CurrentTraceContext currentTraceContext = CurrentTraceContext.Default.create();
        threadPoolExecutor = currentTraceContext.executorService(new ThreadPoolExecutor(
                THREAD_POOL_SIZE, THREAD_POOL_SIZE * 2,
                10, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(256),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }


    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public Response<List<AccountNoticeInfo>> getNotice(@ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> {
            String aid = cacheService.getAccountIdByToken(token);
            tracer.currentSpan().tag("account", aid);
            List<AccountNoticeInfo> noticeInfoList = noticeService.getAllNoticeOperationByAid(aid);
            CountDownLatch countDownLatch = new CountDownLatch(noticeInfoList.size());
            noticeInfoList.forEach(noticeInfo -> threadPoolExecutor.execute(() -> {
                noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
                if (noticeInfo.getTodoRef() == null) {
                    countDownLatch.countDown();
                    tracer.currentSpan().annotate("notice finish: " + noticeInfo.getId());
                    return;
                }
                Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, noticeInfo.getTodoRef());
                if (!r.isSuccess()) {
                    countDownLatch.countDown();
                    tracer.currentSpan().annotate("notice finish: " + noticeInfo.getId());
                    throw new RuntimeException("get todo fail: " + r.getMessage());
                }
                noticeInfo.setTodoList(r.getData());
                tracer.currentSpan().annotate("notice finish: " + noticeInfo.getId());
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
            AccountNoticeInfo notice = noticeService.getNoticeById(id);
            tracer.currentSpan().tag("nid", id + "");
            tracer.currentSpan().tag("title", notice.getTitle());
            if (!"".equals(token)) {
                String aid = cacheService.getAccountIdByToken(token);
                tracer.currentSpan().tag("account", aid);
                noticeService.setNoticeAccountOperation(notice, aid);
                if (notice.getTodoRef() == null) {
                    return notice;
                }
                Response<List<AccountTodoInfo>> r = todoService.getTodoByTokenAndSource(token, notice.getTodoRef());
                if (!r.isSuccess()) {
                    throw new RuntimeException("get todo fail: " + r.getMessage());
                }
                notice.setTodoList(r.getData());
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
            tracer.currentSpan().annotate("get all account operation");
            NoticeAnalysis analysis = new NoticeAnalysis()
                    .setNid(id)
                    .setAccountNotice(noticeService.getAllAccountOperationByNid(id));
            tracer.currentSpan().annotate("get publish account list");
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
            if (notice.getTodoRef() == null || "".equals(notice.getTodoRef())) {
                return analysis;
            }
            tracer.currentSpan().annotate("get all account todo operation");
            Response<List<AccountTodoInfo>> todo = todoService.getTodoBySource(notice.getTodoRef());
            if (!todo.isSuccess()) {
                throw new RuntimeException("get todo fail: " + todo.getMessage());
            }
            tracer.currentSpan().annotate("get all account todo operation finish");
            return analysis.setAccountTodos(todo.getData());
        });
    }

    @GetMapping("/todoRef")
    public Response<Long> getNoticeIdByTodoRef(@RequestParam String ref) {
        return Response.withData(() -> noticeService.getNoticeIdByTodoRef(ref));
    }

}
