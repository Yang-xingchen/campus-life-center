package campuslifecenter.info.controller;

import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.Response;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("对内API")
@RestController
@RequestMapping("/info")
public class InfoApiController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;

    @ApiOperation("添加信息收集")
    @PostMapping("/addCollect")
    public Response<String> addInfoCollect(@RequestBody AddInfoRequest request) {
        return Response.withData(() -> infoService.addInfoCollect(request));
    }

    @ApiOperation("获取填写信息账户列表")
    @PostMapping("/{id}/select")
    public Response<List<String>> select(@PathVariable("id") long id, @RequestParam String text) {
        return Response.withData(() -> accountInfoService.select(id, text));
    }

}
