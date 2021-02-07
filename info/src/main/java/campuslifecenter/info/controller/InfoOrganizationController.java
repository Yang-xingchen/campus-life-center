package campuslifecenter.info.controller;

import brave.Tracer;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.info.entry.OrganizationSaveInfo;
import campuslifecenter.info.service.AccountInfoService;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("组织操作")
@RestWarpController
@RequestMapping("/info")
public class InfoOrganizationController {

    @Autowired
    private InfoService infoService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private Tracer tracer;

    @ApiOperation("获取已保存信息")
    @PostMapping("/getOrganizationSave")
    public List<OrganizationSaveInfo> getByOrganization(@RequestBody List<Long> ids, @RequestParam int id) {
        tracer.currentSpan().tag("organization", id + "");
        return accountInfoService.getSaveByOrganization(ids, id);
    }
}
