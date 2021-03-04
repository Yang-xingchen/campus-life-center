package campuslifecenter.todo.controller;


import campuslifecenter.common.model.IdName;
import campuslifecenter.common.model.Response;
import campuslifecenter.todo.entry.ConditionTodo;
import campuslifecenter.todo.service.CacheService;
import campuslifecenter.todo.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo/condition")
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
    public Response<String> create(@RequestBody ConditionTodo conditionTodo) {
        return Response.withData(() -> conditionService.create(conditionTodo));
    }

    @PostMapping("/search")
    public List<IdName<String>> search(@RequestBody ConditionTodo conditionTodo) {
        return conditionService.getAccounts(conditionTodo)
                .stream()
                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                .collect(Collectors.toList());
    }

}
