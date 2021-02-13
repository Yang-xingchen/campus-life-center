package campuslifecenter.comment.model;

import campuslifecenter.comment.entry.Comment;

import java.util.List;

public class CommentInfo extends Comment {

    private String accountName;
    private List<CommentInfo> children;

    public static CommentInfo createByComment(Comment comment) {
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.withAid(comment.getAid())
                .withContent(comment.getContent())
                .withId(comment.getId())
                .withParent(comment.getParent())
                .withPublishTime(comment.getPublishTime());
        return commentInfo;
    }

    public String getAccountName() {
        return accountName;
    }

    public CommentInfo setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public List<CommentInfo> getChildren() {
        return children;
    }

    public CommentInfo setChildren(List<CommentInfo> children) {
        this.children = children;
        return this;
    }
}
