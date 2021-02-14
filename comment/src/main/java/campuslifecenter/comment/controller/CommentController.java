package campuslifecenter.comment.controller;

import campuslifecenter.comment.model.CommentInfo;
import campuslifecenter.comment.model.Reply;
import campuslifecenter.comment.service.CacheService;
import campuslifecenter.comment.service.CommentService;
import campuslifecenter.common.model.RestWarpController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("评论")
@RestWarpController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CacheService cacheService;

    @GetMapping("/ref/{ref}")
    public List<CommentInfo> getByRef(@PathVariable("ref") String ref) {
        return commentService.getByRef(ref);
    }

    @PostMapping("/ref/{ref}/reply")
    public CommentInfo reply(@RequestParam String token, @PathVariable("ref")String ref, @RequestBody Reply reply) {
        String aid = cacheService.getAccountIdByToken(token);
        return commentService.reply(ref, aid, reply.getContent());
    }

    @PostMapping("/{id}/reply")
    public CommentInfo reply(@RequestParam String token, @PathVariable("id") long id, @RequestBody Reply reply) {
        String aid = cacheService.getAccountIdByToken(token);
        return commentService.reply(id, aid, reply.getContent());
    }

}
