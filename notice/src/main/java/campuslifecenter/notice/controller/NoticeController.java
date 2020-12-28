package campuslifecenter.notice.controller;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private PublishService publishService;

    @ApiOperation("根据token获取收到的通知")
    @GetMapping("/get/{token}")
    public Response<List<AccountNoticeInfo>> getNotice(@PathVariable("token") String token) {
        Response<AccountInfo> response = accountService.info(token);
        if (!response.isSuccess()) {
            return new Response<List<AccountNoticeInfo>>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("失败");
        }
        return Response.withData(noticeService.getAllNoticeByAid(response.getData()));
    }

    @ApiOperation("获取通知")
    @GetMapping("/{id}")
    public Response<AccountNoticeInfo> getNotice(@PathVariable("id") long id) {
        return Response.withData(noticeService.getNoticeById(id));
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
