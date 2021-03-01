package campuslifecenter.search.service;

import campuslifecenter.common.model.Response;
import campuslifecenter.search.model.NoticeInfo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "notice", path = "/notice", contextId = "notice")
public interface NoticeService {

    @GetMapping("/{id}")
    Response<NoticeInfo> getNotice(@ApiParam("通知id") @PathVariable("id") long id);

}
