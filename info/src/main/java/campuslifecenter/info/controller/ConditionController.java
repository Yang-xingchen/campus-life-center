package campuslifecenter.info.controller;


import campuslifecenter.common.model.IdName;
import campuslifecenter.common.model.Response;
import campuslifecenter.info.entry.ConditionInfo;
import campuslifecenter.info.service.CacheService;
import campuslifecenter.info.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/info/condition")
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
    public Response<String> create(@RequestBody ConditionInfo conditionInfo) {
        return Response.withData(() -> conditionService.create(conditionInfo));
    }

    @PostMapping("/search")
    public List<IdName<String>> search(@RequestBody ConditionInfo conditionInfo) {
        return conditionService.getAccounts(conditionInfo)
                .stream()
                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                .collect(Collectors.toList());
    }

}
