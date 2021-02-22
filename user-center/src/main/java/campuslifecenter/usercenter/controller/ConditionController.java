package campuslifecenter.usercenter.controller;

import campuslifecenter.common.model.Response;
import campuslifecenter.usercenter.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account/condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    @GetMapping("/{ref}/accounts")
    public List<String> getAccount(@PathVariable("ref") String ref) {
        return conditionService.getAccounts(ref);
    }

    @GetMapping("/{ref}/publish")
    public boolean publish(@PathVariable("ref") String ref) {
        return conditionService.publish(ref);
    }

    @PostMapping("/create")
    public Response<String> create(@RequestBody List<String> aid) {
        return Response.withData(() -> conditionService.create(aid));
    }

}
