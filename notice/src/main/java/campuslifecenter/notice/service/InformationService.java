package campuslifecenter.notice.service;

import campuslifecenter.notice.model.InformationCollect;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "info", path = "/info", contextId = "info")
public interface InformationService {
    /**
     * TODO
     */
    @PostMapping
    Response<String> addInfoCollect(List<InformationCollect> informationList);

    @PostMapping
    Response<List<String>> getAccountByInfo(PublishNotice.PublishInfo dynamicInfoObserve);
}
