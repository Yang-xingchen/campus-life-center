package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.AccountSubmit;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("账户操作")
@RestWarpController
@RequestMapping("/info")
public class InfoAccountController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取收集项目及已提交信息")
    @GetMapping("/get")
    public InfoItem.CompositeItem get(@RequestParam String ref, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("ref", ref);
        long rootId = infoService.getRoot(ref);
        return accountInfoService.getSubmit(aid, rootId);
    }

    @ApiOperation("获取已保存信息")
    @PostMapping("/getAccountSave")
    public List<AccountSaveInfo> getByAccount(@RequestBody List<Long> ids, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        return accountInfoService.getSaveByAccount(ids, aid);
    }

    @ApiOperation("提交")
    @PostMapping("/submit")
    public Boolean submit(@RequestBody List<AccountSubmit> infos, @RequestParam String token, @RequestParam String ref) {
        String aid = cacheService.getAccountIdByToken(token);
        tracer.currentSpan().tag("aid", aid);
        tracer.currentSpan().tag("ref", ref);
        long root = infoService.getRoot(ref);
        infos.stream().peek(info -> info.setAid(aid)).forEach(info -> info.setRoot(root));
        return accountInfoService.submit(infos);
    }

}
