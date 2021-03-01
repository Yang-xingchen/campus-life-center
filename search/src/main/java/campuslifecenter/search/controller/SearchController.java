package campuslifecenter.search.controller;

import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.search.model.NoticeSearch;
import campuslifecenter.search.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestWarpController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/notice")
    public NoticeSearch notice() {
        // todo
        return null;
    }

}
