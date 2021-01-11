package campuslifecenter.info.controller;

import campuslifecenter.info.model.AddInfoRequest;
import campuslifecenter.info.model.InfoCollect;
import campuslifecenter.info.model.Response;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info")
public class InformationController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private CacheService cacheService;

    @ApiOperation("添加信息收集")
    @PostMapping("/addCollect")
    public Response<String> addInfoCollect(@RequestBody AddInfoRequest request) {
        return Response.withData(() -> infoService.addInfoCollect(request));
    }

    @ApiOperation("获取填写相似信息账户列表")
    @PostMapping("/{id}/select")
    public Response<List<String>> select(@PathVariable("id") long id, @RequestParam String text) {
        return Response.withData(() -> infoService.select(id, text));
    }

    @ApiOperation("获取填写信息")
    @GetMapping("/get")
    public Response<InfoCollect> get(@RequestParam String ref, @RequestParam String token) {
        return Response.withData(() -> infoService.getAccountInfo(ref, cacheService.getAccountIdByToken(token)));
    }

    @ApiOperation("获取来源下所有填写的信息")
    @GetMapping("/getByRef")
    public Response<InfoCollect> get(@RequestParam String ref) {
        return Response.withData(() -> infoService.getAllAccountInfo(ref));
    }

}
