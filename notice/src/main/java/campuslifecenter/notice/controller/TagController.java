package campuslifecenter.notice.controller;

import campuslifecenter.common.model.RestWarpController;
import campuslifecenter.notice.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@RestWarpController
@RequestMapping("/notice/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("tag列表")
    @GetMapping("")
    public Set<String> tagList() {
        return tagService.tagList();
    }

}
