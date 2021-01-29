package campuslifecenter.info.controller;

import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.model.InfoCollectRequest;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("对内API")
@RestWarpController
@RequestMapping("/info")
public class InfoApiController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;

    @ApiOperation("添加信息收集")
    @PostMapping("/addCollect")
    public String addInfoCollect(@RequestBody InfoCollectRequest.AddInfoRequest request) {
        return infoService.addCollect(request);
    }

    @ApiOperation("获取填写信息账户列表")
    @PostMapping("/{id}/select")
    public List<String> select(@PathVariable("id") long id, @RequestParam String text) {
        return accountInfoService.select(id, text);
    }

    @ApiOperation("获取收集名称")
    @GetMapping("/{id}/name")
    public Response<String> getInfo(@PathVariable("id") Long id) {
        return Response.withData(() -> infoService.getInfoItem(id, null).getName());
    }

}
