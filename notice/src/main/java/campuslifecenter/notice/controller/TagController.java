package campuslifecenter.notice.controller;

import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/notice/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("tag列表")
    @GetMapping("")
    public Response<Set<String>> tagList() {
        return Response.withData(() -> tagService.tagList());
    }

}
