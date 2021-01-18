package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.info.entry.AccountInfo;
import campuslifecenter.info.entry.InfoAccountList;
import campuslifecenter.info.model.InfoCollect;
import campuslifecenter.info.model.Response;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("账户操作")
@RestController
@RequestMapping("/info")
public class InfoAccountController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取收集项目及已提交信息")
    @GetMapping("/get")
    public Response<InfoCollect> get(@RequestParam String ref, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("source", ref);
        return Response.withData(() -> infoService.getCollectList(ref, aid));
    }

    @ApiOperation("获取已保存信息")
    @PostMapping("/getAccountSave")
    public Response<List<AccountInfo>> getByAccount(@RequestBody List<Long> ids, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        return Response.withData(() -> infoService.getAccountSaveInfo(ids, aid));
    }

    @ApiOperation("提交")
    @PostMapping("/submit")
    public Response<Boolean> submit(@RequestBody List<InfoAccountList> infos, @RequestParam String token, @RequestParam String ref) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("source", ref);
        infos.stream().peek(info -> info.setAid(aid)).forEach(info -> info.setSource(ref));
        return Response.withData(() -> infoService.submit(infos));
    }

}