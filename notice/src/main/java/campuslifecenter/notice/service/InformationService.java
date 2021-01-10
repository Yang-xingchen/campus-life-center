package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AddInfoRequest;
import campuslifecenter.notice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "info", path = "/info", contextId = "info")
public interface InformationService {


    @PostMapping("/addCollect")
    Response<String> addInfoCollect(AddInfoRequest informationList);

    @PostMapping("/{id}/select")
    Response<List<String>> select(@PathVariable("id") long id, @RequestParam String text);

}
