package campuslifecenter.comment.service;

import campuslifecenter.comment.model.CommentInfo;

import java.util.List;

public interface CommentService {

    List<CommentInfo> getByRef(String ref);

    CommentInfo getComment(long id);

    CommentInfo reply(long id, String aid, String content);

    CommentInfo reply(String ref, String aid, String content);

}
