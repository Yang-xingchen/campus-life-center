package campuslifecenter.info.controller;

import campuslifecenter.common.model.Response;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.model.InfoCollectRequest;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.ConditionService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Api("对内API")
@RestWarpController
@RequestMapping("/info")
public class InfoApiController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private ConditionService conditionService;

    @Value("${info.redis.cache.collect-name}")
    private String REF_NAME_PREFIX;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("添加信息收集")
    @PostMapping("/addCollect")
    public Response<String> addInfoCollect(@RequestBody InfoCollectRequest.AddInfoRequest request) {
        return Response.withData(() -> infoService.addCollect(request));
    }

    @ApiOperation("获取填写信息账户列表")
    @PostMapping("/{id}/select")
    public List<String> select(@PathVariable("id") long id, @RequestParam int type, @RequestParam String text) {
        return conditionService.select(id, type, text);
    }

    @ApiOperation("获取收集名称")
    @GetMapping("/{id}/name")
    public Response<String> getInfo(@PathVariable("id") Long id) {
        return Response.withData(() -> infoService.getInfoItem(id, null).getName());
    }

    @ApiOperation("获取引用名称")
    @GetMapping("/ref/{ref}/name")
    public Response<String> getRefName(@RequestParam String ref) {
        return Response.withData(() -> {
            long id = infoService.getRoot(ref);
            String name = infoService.getInfoItem(id, null).getName();
            redisTemplate.opsForValue().set(REF_NAME_PREFIX + ref, name, 1, TimeUnit.DAYS);
            return name;
        });
    }

    @ApiOperation("更新收集成员")
    @PostMapping("/ref/{ref}/updateAccount")
    public boolean updateAccount(@PathVariable("ref") String ref, @RequestBody List<String> aids) {
        long id = infoService.getRoot(ref);
        return infoService.updateCollectAccount(id, aids);
    }

}
