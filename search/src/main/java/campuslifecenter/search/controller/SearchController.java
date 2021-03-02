package campuslifecenter.search.controller;

import campuslifecenter.common.model.LazyList;
import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.search.model.NoticeSearch;
import campuslifecenter.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestWarpController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/notice")
    public LazyList<NoticeSearch> notice(@RequestParam String keyword, @RequestParam int page) {
        return searchService.searchNotice(keyword, page);
    }

}
