package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.info.model.InfoItem;
import campuslifecenter.info.model.InfoSourceCollect;
import campuslifecenter.info.model.Response;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("发布者操作")
@RestController
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
    public Response<InfoSourceCollect> get(@RequestParam String ref, @RequestParam long rootId) {
        tracer.currentSpan().tag("source", ref);
        return Response.withData(() -> accountInfoService.getSubmitByRef(ref, rootId));
    }

    @ApiOperation("获取现有信息列表")
    @GetMapping("/getExistInfo")
    public Response<List<InfoItem>> getExistInfo() {
        return Response.withData(() -> infoService.getExistInfo());
    }
}
