package campuslifecenter.notice.controller;

import campuslifecenter.common.model.IdName;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.entry.ConditionOrganization;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice/organization/condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;
    @Autowired
    private CacheService cacheService;

    @GetMapping("/{ref}/accounts")
    public List<String> getAccount(@PathVariable("ref") String ref) {
        return conditionService.getAccounts(ref);
    }

    @GetMapping("/{ref}/publish")
    public boolean publish(@PathVariable("ref") String ref) {
        return conditionService.publish(ref);
    }

    @PostMapping("/create")
    public Response<String> create(@RequestBody ConditionOrganization conditionOrganization) {
        return Response.withData(() -> conditionService.create(conditionOrganization));
    }

    @PostMapping("/search")
    public List<IdName<String>> search(@RequestBody ConditionOrganization conditionOrganization) {
        return conditionService.getAccounts(conditionOrganization)
                .stream()
                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                .collect(Collectors.toList());
    }

}
