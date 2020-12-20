package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.Notice;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class NoticeInfo implements Serializable {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "创建者id")
    private String creatorId;

    @ApiModelProperty(value = "创建者名称")
    private String creatorName;

    @ApiModelProperty(value = "组织id")
    private Integer organizationId;

    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    @ApiModelProperty(value = "可见性: 0:公开; 1:仅通知账户")
    private String visibility;

    @ApiModelProperty(value = "发布类型: 0:组织内成员; 1:订阅列表(见account_subscribe); 2: 静态名单列表(account_notice); 3: 动态条件(见notice_condition)")
    private String publicType;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "重要程度: 0:最低; 5:最高")
    private Byte importance;

    @ApiModelProperty(value = "通知日期")
    private Date time;

    @ApiModelProperty(value = "正文内容")
    private String content;

    public static NoticeInfo withNotice(Notice notice) {
        return new NoticeInfo()
                .setId(notice.getId())
                .setCreatorId(notice.getCreator())
                .setOrganizationId(notice.getOrganization())
                .setVisibility(notice.getVisibility())
                .setPublicType(notice.getPublicType())
                .setCreateTime(notice.getCreateTime())
                .setTitle(notice.getTitle())
                .setImportance(notice.getImportance())
                .setTime(notice.getTime())
                .setContent(notice.getContent());
    }

    public Integer getId() {
        return id;
    }

    public NoticeInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public NoticeInfo setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public NoticeInfo setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public NoticeInfo setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public NoticeInfo setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public String getVisibility() {
        return visibility;
    }

    // 0:公开; 1:仅通知账户
    public NoticeInfo setVisibility(Byte visibility) {
        switch (visibility) {
            case 0 -> this.visibility = "公开";
            case 1 -> this.visibility = "仅通知账户";
            default -> throw new IllegalArgumentException(visibility + " is undefined");
        }
        return this;
    }

    public String getPublicType() {
        return publicType;
    }

    // 0:组织内成员; 1:订阅列表(见account_subscribe); 2: 静态名单列表(account_notice); 3: 动态条件(见notice_condition)
    public NoticeInfo setPublicType(Byte publicType) {
        switch (publicType) {
            case 0 -> this.publicType = "组织内成员";
            case 1 -> this.publicType = "订阅列表";
            case 2 -> this.publicType = "静态名单列表";
            case 3 -> this.publicType = "动态条件";
            default -> throw new IllegalArgumentException(publicType + " is undefined");
        }
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public NoticeInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoticeInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public Byte getImportance() {
        return importance;
    }

    public NoticeInfo setImportance(Byte importance) {
        this.importance = importance;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public NoticeInfo setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NoticeInfo setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NoticeInfo that = (NoticeInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NoticeInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                '}';
    }
}
