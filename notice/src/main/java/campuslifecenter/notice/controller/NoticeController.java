package campuslifecenter.notice.controller;


import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublicNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private NoticeService noticeService;

    @ApiOperation("根据token获取收到的通知")
    @PostMapping("/get")
    public Response<List<AccountNoticeInfo>> getNotice(@RequestBody String token) {
        Response<AccountInfo> response = accountService.info(token);
        if (!response.isSuccess()) {
            return new Response<List<AccountNoticeInfo>>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("失败");
        }
        return Response.withData(noticeService.getAllNoticeByAid(response.getData()));
    }

    @ApiOperation("发布通知")
    @PostMapping("/public_notice")
    public Response<?> publicNotice(@RequestBody PublicNotice publicNotice) {
        Response<AccountInfo> accountInfo = accountService.info(publicNotice.getToken());
        if (!accountInfo.isSuccess()) {
            return new Response<>()
                    .setSuccess(false)
                    .setMessage("illegal account");
        }
        publicNotice.getNotice().setCreator(accountInfo.getData().getSignId());
        return Response.withData(noticeService.publicNotice(publicNotice));
    }

}
