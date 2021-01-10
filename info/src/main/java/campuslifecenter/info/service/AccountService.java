package campuslifecenter.info.service;

import campuslifecenter.info.model.AccountInfo;
import campuslifecenter.info.model.Response;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-center", path = "/account", contextId = "account")
public interface AccountService {

    @GetMapping("/info/{token}")
    Response<AccountInfo> info(@ApiParam("token") @PathVariable String token);

    @GetMapping("/{id}/info")
    Response<AccountInfo> infoById(@ApiParam("id") @PathVariable String id);

    @PostMapping("/infos")
    Response<List<AccountInfo>> infoByIds(List<String> ids);
}
