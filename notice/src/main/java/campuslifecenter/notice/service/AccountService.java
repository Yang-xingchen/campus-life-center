package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-center", path = "/account", contextId = "account")
public interface AccountService {

    @GetMapping("/info/{token}")
    Response<AccountInfo> info(@ApiParam("token") @PathVariable String token);

    @GetMapping("/{id}/info")
    Response<AccountInfo> infoById(@ApiParam("id") @PathVariable String id);

}
