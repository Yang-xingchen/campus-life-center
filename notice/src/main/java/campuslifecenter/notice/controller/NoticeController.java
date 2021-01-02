package campuslifecenter.notice.controller;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private PublishService publishService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    public static final String TOKEN_PREFIX = "TOKEN_";

    private String getAccountNameByToken(String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountInfo> response = accountService.info(token);
                    if (!response.isSuccess()) {
                        throw new IllegalArgumentException("account not found:" + response.getMessage());
                    }
                    return response.getData().getSignId();
                });
    }

    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/get/{token}")
    public Response<List<AccountNoticeInfo>> getNotice(@PathVariable("token") String token) {
        return Response.withData(() -> {
            String aid = getAccountNameByToken(token);
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
    public Response<AccountNoticeInfo> getNotice(@PathVariable("id") long id,
                                                 @RequestParam(required = false, defaultValue = "") String token) {
        return Response.withData(() -> {
            AccountNoticeInfo notice = noticeService.getNoticeById(id);
            if (!"".equals(token)) {
                String aid = getAccountNameByToken(token);
                noticeService.setNoticeAccountOperation(notice, aid);
                todoService.setAccountTodoOperation(notice, aid);
            }
            return notice;
        });
    }

    @ApiOperation("发布通知")
    @PostMapping("/publicNotice")
    public Response<?> publicNotice(@RequestBody PublishNotice publishNotice) {
        Response<AccountInfo> accountInfo = accountService.info(publishNotice.getToken());
        if (!accountInfo.isSuccess()) {
            return new Response<>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("illegal account");
        }
        publishNotice.getNotice().setCreator(accountInfo.getData().getSignId());
        try {
            publishNotice.setAccountList(publishService.publicAccountStream(publishNotice).collect(Collectors.toList()));
        } catch (RuntimeException e) {
            return new Response<>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("get account list fail: " + e.getMessage());
        }
        return Response.withData(noticeService.publicNotice(publishNotice));
    }

    @ApiOperation("获取收到通知的成员")
    @PostMapping("/getPublicNoticeAccount")
    public Response<List<AccountInfo>> getPublicNoticeAccount(@RequestBody PublishNotice publishNotice) {
        return Response.withData(() -> {
            List<String> ids = publishService.publicAccountStream(publishNotice).collect(Collectors.toList());
            Response<List<AccountInfo>> response = accountService.infoByIds(ids);
            if (!response.isSuccess()) {
                throw new RuntimeException(response.getMessage());
            }
            return response.getData();
        });
    }

}
