package campuslifecenter.comment.controller;

import campuslifecenter.comment.model.CommentInfo;
import campuslifecenter.comment.service.CommentService;
import campuslifecenter.common.model.RestWarpController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api("评论")
@RestWarpController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/ref/{ref}")
    public List<CommentInfo> getByRef(@PathVariable("ref") String ref) {
        return commentService.getByRef(ref);
    }

}
