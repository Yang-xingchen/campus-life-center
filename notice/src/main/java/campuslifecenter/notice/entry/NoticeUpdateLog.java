package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class NoticeUpdateLog implements Serializable {
    @ApiModelProperty(value = "id")
    private Long uid;

    @ApiModelProperty(value = "id")
    private Long nid;

    private Date updateTime;

    private String title;

    @ApiModelProperty(value = ": 0,; 5,")
    private Integer importance;

    private Date noticeTime;

    private String content;

    private static final long serialVersionUID = 1L;

    public Long getUid() {
        return uid;
    }

    public NoticeUpdateLog withUid(Long uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getNid() {
        return nid;
    }

    public NoticeUpdateLog withNid(Long nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public NoticeUpdateLog withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public NoticeUpdateLog withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getImportance() {
        return importance;
    }

    public NoticeUpdateLog withImportance(Integer importance) {
        this.setImportance(importance);
        return this;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public NoticeUpdateLog withNoticeTime(Date noticeTime) {
        this.setNoticeTime(noticeTime);
        return this;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getContent() {
        return content;
    }

    public NoticeUpdateLog withContent(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", nid=").append(nid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", title=").append(title);
        sb.append(", importance=").append(importance);
        sb.append(", noticeTime=").append(noticeTime);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}