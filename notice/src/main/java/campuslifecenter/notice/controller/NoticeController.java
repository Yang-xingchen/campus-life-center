package campuslifecenter.notice.controller;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.NoticeTodoKey;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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
    private NoticeTodoService noticeTodoService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;
    @Autowired
    private CacheService cacheService;


    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/getAll")
    public Response<List<AccountNoticeInfo>> getNotice(@ApiParam("token") @RequestParam String token) {
        return Response.withData(() -> {
            String aid = cacheService.getAccountIdByToken(token);
            List<AccountNoticeInfo> noticeInfoList = noticeService.getAllNoticeOperationByAid(aid);
            noticeInfoList.forEach(noticeInfo -> {
                noticeInfo.merge(noticeService.getNoticeById(noticeInfo.getId()));
                todoService.setAccountTodoOperation(noticeInfo, aid);
            });
            return noticeInfoList;
        });
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public Response<AccountNoticeInfo> getNotice(@ApiParam("通知id") @PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        return Response.withData(() -> {
            AccountNoticeInfo notice = noticeService.getNoticeById(id);
            if (!"".equals(token)) {
                String aid = cacheService.getAccountIdByToken(token);
                noticeService.setNoticeAccountOperation(notice, aid);
                todoService.setAccountTodoOperation(notice, aid);
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
            AccountNoticeInfo notice = noticeService.getNoticeById(id);
            if (!Objects.equals(aid, notice.getCreator())) {
                throw new IllegalArgumentException("illegal account");
            }
            return new NoticeAnalysis()
                    .setNid(id)
                    .setAccountTodos(todoService.getAccountTodoByNid(id))
                    .setAccountNotice(noticeService.getAllAccountOperationByNid(id))
                    .setPublishAccountList(
                            Stream.of(
                                    publishService.publicTodoStream(publishService.getPublishTodoByNid(id)),
                                    publishService.publicInfoStream(publishService.getPublishInfoByNid(id)),
                                    publishService.publicOrganizationStream(publishService.getPublishOrganizationByNid(id))
                            ).reduce(Stream::concat).get().collect(Collectors.toList())
                    );
        });
    }


    @ApiOperation("发布通知")
    @PostMapping("/publicNotice")
    public Response<?> publicNotice(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        publishNotice.getNotice().setCreator(cacheService.getAccountIdByToken(publishNotice.getToken()));
        try {
            publishNotice.setAccountList(publishService
                    .publicAccountStream(publishNotice)
                    .map(PublishAccount::getAccounts)
                    .flatMap(List::stream)
                    .map(IdName::getId)
                    .collect(toList())
            );
        } catch (RuntimeException e) {
            return new Response<>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("get account list fail: " + e.getMessage());
        }
        return Response.withData(() -> noticeService.publicNotice(publishNotice));
    }

    @ApiOperation("获取收到通知的成员")
    @PostMapping("/getPublicNoticeAccount")
    public Response<List<PublishAccount<?>>> getPublicNoticeAccount(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        return Response.withData(() -> publishService
                .publicAccountStream(publishNotice)
                .collect(toList()));
    }

}
