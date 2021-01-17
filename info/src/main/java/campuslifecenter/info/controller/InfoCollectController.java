package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.info.model.InfoCollect;
import campuslifecenter.info.model.Response;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("收集操作")
@RestController
@RequestMapping("/info")
public class InfoCollectController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取来源下所有填写的信息")
    @GetMapping("/getAccountSubmit")
    public Response<InfoCollect> get(@RequestParam String ref) {
        tracer.currentSpan().tag("source", ref);
        return Response.withData(() -> infoService.getAllAccountSubmit(ref));
    }
}
