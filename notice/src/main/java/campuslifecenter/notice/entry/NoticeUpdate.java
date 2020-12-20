package campuslifecenter.notice.entry;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class NoticeUpdate implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer uid;

    @ApiModelProperty(value = "id")
    private Integer nid;

    private Date updateTime;

    private String title;

    @ApiModelProperty(value = ": 0:; 5:")
    private Byte importance;

    private Date time;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getUid() {
        return uid;
    }

    public NoticeUpdate withUid(Integer uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNid() {
        return nid;
    }

    public NoticeUpdate withNid(Integer nid) {
        this.setNid(nid);
        return this;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public NoticeUpdate withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public NoticeUpdate withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Byte getImportance() {
        return importance;
    }

    public NoticeUpdate withImportance(Byte importance) {
        this.setImportance(importance);
        return this;
    }

    public void setImportance(Byte importance) {
        this.importance = importance;
    }

    public Date getTime() {
        return time;
    }

    public NoticeUpdate withTime(Date time) {
        this.setTime(time);
        return this;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public NoticeUpdate withContent(String content) {
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
        sb.append(", time=").append(time);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}