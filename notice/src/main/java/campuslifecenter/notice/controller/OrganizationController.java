package campuslifecenter.notice.controller;

import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.OrganizationSubscribeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api("组织")
@RestWarpController
@RequestMapping("/notice")
public class OrganizationController {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private OrganizationSubscribeService subscribeService;

    @GetMapping("/organization/{oid}/subscribe")
    public boolean subscribe(@PathVariable("oid") int oid, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        return subscribeService.subscribe(aid, oid);
    }

    @GetMapping("/organization/{oid}/cancelSubscribe")
    public boolean cancelSubscribe(@PathVariable("oid") int oid, @RequestParam String token) {
        String aid = cacheService.getAccountIdByToken(token);
        return subscribeService.cancelSubscribe(aid, oid);
    }

}
