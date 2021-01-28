package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("发布者操作")
@RestWarpController
@RequestMapping("/info")
public class PublisherController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取来源下所有填写的信息")
    @GetMapping("/getAccountSubmit")
    public InfoSourceCollect get(@RequestParam String ref, @RequestParam long rootId) {
        tracer.currentSpan().tag("source", ref);
        return accountInfoService.getSubmitByRef(ref, rootId);
    }

    @ApiOperation("获取现有信息列表")
    @GetMapping("/getExistInfo")
    public List<InfoItem> getExistInfo() {
        return infoService.getExistInfo();
    }
}
