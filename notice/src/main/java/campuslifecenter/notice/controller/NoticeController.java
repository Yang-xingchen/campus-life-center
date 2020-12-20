package campuslifecenter.notice.controller;


import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.NoticeInfo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.NoticeService;
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

    @PostMapping("/get")
    public Response<List<NoticeInfo>> getNotice(@RequestBody String token) {
        Response<AccountInfo> response = accountService.info(token);
        if (!response.isSuccess()) {
            return new Response<List<NoticeInfo>>()
                    .setSuccess(false)
                    .setCode(400)
                    .setMessage("失败");
        }
        return Response.withData(noticeService.getNoticeByAid(response.getData()));
    }

}
