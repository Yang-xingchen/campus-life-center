package campuslifecenter.notice.controller;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.AuthException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeUpdateLog;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api("更新")
@RestWarpController
@RequestMapping("/notice")
public class UpdateController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private TracerUtil tracerUtil;

    @ApiOperation("获取更新记录")
    @GetMapping("/{id}/updateLog")
    public List<NoticeUpdateLog> getUpdateLog(@PathVariable("id") long id) {
        tracerUtil.getSpan().tag("nid", id + "");
        return noticeService.updateLog(id);
    }


    @ApiOperation("更新操作")
    @PostMapping("/{id}/updateOperation")
    public Boolean updateOperation(@ApiParam("通知id") @PathVariable("id") long id,
                                   @ApiParam("token") @RequestParam(required = false, defaultValue = "") String token,
                                   @RequestBody AccountNotice accountNotice) {
        String aid = cacheService.getAccountIdByToken(token);
        tracerUtil.getSpan().tag("account", aid);
        tracerUtil.getSpan().tag("nid", id + "");
        AuthException.checkThrow(aid, accountNotice.getAid());
        return noticeService.updateAccountOperation(accountNotice);
    }

    @ApiOperation("更新内容")
    @PostMapping("/{id}/update")
    public boolean update(@RequestBody Notice notice, @RequestParam String token, @PathVariable("id") long id) {
        String aid = cacheService.getAccountIdByToken(token);
        AccountNoticeInfo oldNotice = noticeService.getNoticeById(id);
        AuthException.checkThrow(oldNotice.getCreator(), aid);
        noticeService.update(notice, oldNotice);
        return true;
    }
}
