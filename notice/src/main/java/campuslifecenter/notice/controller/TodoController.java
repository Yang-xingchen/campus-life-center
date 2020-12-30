package campuslifecenter.notice.controller;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.NoticeTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private NoticeTodoService todoService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/todoByToken/{token}")
    public Response<List<AccountNoticeInfo.AccountTodo>> getAccountByToken(@PathVariable("token") String token) {
        Response<AccountInfo> accountInfo = accountService.info(token);
        if (!accountInfo.isSuccess()) {
            return new Response<List<AccountNoticeInfo.AccountTodo>>()
                    .setCode(400).setMessage("token not found").setSuccess(false);
        }
        return Response.withData(() -> todoService.getTodoByAccount(accountInfo.getData().getSignId()));
    }

}
