package campuslifecenter.todo.controller;


import campuslifecenter.common.model.Response;
import campuslifecenter.todo.entry.ConditionTodo;
import campuslifecenter.todo.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/condition")
public class ConditionController {

    @Autowired
    private ConditionService conditionService;

    @GetMapping("/{ref}/accounts")
    public List<String> getAccount(@PathVariable("ref") String ref) {
        return conditionService.getAccounts(ref);
    }


    @PostMapping("/create")
    public Response<String> create(@RequestBody ConditionTodo conditionTodo) {
        return Response.withData(() -> conditionService.create(conditionTodo));
    }

}
