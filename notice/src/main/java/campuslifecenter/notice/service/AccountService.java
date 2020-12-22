package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-center", path = "/account", contextId = "account")
public interface AccountService {

    @PostMapping("/info")
    Response<AccountInfo> info(@RequestBody String token);

}
