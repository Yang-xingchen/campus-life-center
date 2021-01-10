package campuslifecenter.notice.controller;

import campuslifecenter.notice.model.IdName;
import campuslifecenter.notice.model.PublishAccount;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.PublishService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/notice/publish")
public class PublishController {

    @Autowired
    private PublishService publishService;
    @Autowired
    private CacheService cacheService;


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
        return Response.withData(() -> publishService.publicNotice(publishNotice));
    }

    @ApiOperation("获取收到通知的成员")
    @PostMapping("/getPublicNoticeAccount")
    public Response<List<PublishAccount<?>>> getPublicNoticeAccount(@ApiParam("发布内容") @RequestBody PublishNotice publishNotice) {
        return Response.withData(() -> publishService
                .publicAccountStream(publishNotice)
                .collect(toList()));
    }
}
