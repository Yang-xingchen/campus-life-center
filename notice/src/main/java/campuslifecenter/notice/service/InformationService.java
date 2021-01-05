package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.model.InformationConditions;
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
    Response<String> addInfoCollect(List<InformationConditions> informationList);

    @PostMapping
    Response<List<String>> getAccountByInfo(PublishInfo dynamicInfoObserve);
}
