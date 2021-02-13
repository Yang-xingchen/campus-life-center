package campuslifecenter.comment.service;

import campuslifecenter.comment.model.CommentInfo;

import java.util.List;

public interface CommentService {
    List<CommentInfo> getByRef(String ref);
}
