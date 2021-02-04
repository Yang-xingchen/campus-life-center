package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.scheduling.annotation.Async;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountNoticeInfo extends NoticeInfo {

    @ApiModelProperty("账户id")
    private String aid;

    @ApiModelProperty("是否已读")
    private Boolean looked;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty("是否删除")
    private Boolean del;

    @ApiModelProperty("相对重要度")
    private Integer relativeImportance;

    public static class Info extends NoticeInfoKey {
        private String name;

        public String getName() {
            return name;
        }

        public Info setName(String name) {
            this.name = name;
            return this;
        }
    }

    public static AccountNoticeInfo createByNotice(Notice notice) {
        Objects.requireNonNull(notice, "notice not find");
        AccountNoticeInfo info = new AccountNoticeInfo();
        info.setNotice(notice);
        return info;
    }

    public static AccountNoticeInfo createByAccountNotice(AccountNotice notice) {
        Objects.requireNonNull(notice, "account notice not find");
        return new AccountNoticeInfo()
                .setAccountOperation(notice);
    }

    public void merge(AccountNoticeInfo other) {
        // notice
        setId(Optional.ofNullable(other.getId()).orElse(getId()));
        setCreator(Optional.ofNullable(other.getCreator()).orElse(getCreator()));
        setOrganization(Optional.ofNullable(other.getOrganization()).orElse(getOrganization()));
        setVisibility(Optional.ofNullable(other.getVisibility()).orElse(getVisibility()));
        setImportance(Optional.ofNullable(other.getImportance()).orElse(getImportance()));
        setPublicType(Optional.ofNullable(other.getPublicType()).orElse(getPublicType()));
        setVersion(Optional.ofNullable(other.getVersion()).orElse(getVersion()));
        setTitle(Optional.ofNullable(other.getTitle()).orElse(getTitle()));
        setContentType(Optional.ofNullable(other.getContentType()).orElse(getContentType()));
        setCreateTime(Optional.ofNullable(other.getCreateTime()).orElse(getCreateTime()));
        setStartTime(Optional.ofNullable(other.getStartTime()).orElse(getStartTime()));
        setEndTime(Optional.ofNullable(other.getEndTime()).orElse(getEndTime()));
        setTodoRef(Optional.ofNullable(other.getTodoRef()).orElse(getTodoRef()));
        setContent(Optional.ofNullable(other.getContent()).orElse(getContent()));
        setFileRef(Optional.ofNullable(other.getFileRef()).orElse(getContent()));
        // account
        setAid(Optional.ofNullable(other.getAid()).orElse(getAid()));
        setLooked(Optional.ofNullable(other.getLooked()).orElse(getLooked()));
        setTop(Optional.ofNullable(other.getTop()).orElse(getTop()));
        setDel(Optional.ofNullable(other.getDel()).orElse(getDel()));
        // other
        setOrganizationName(Optional.ofNullable(other.getOrganizationName()).orElse(getOrganizationName()));
        setCreatorName(Optional.ofNullable(other.getCreatorName()).orElse(getCreatorName()));
        setRelativeImportance(Optional.ofNullable(other.getRelativeImportance()).orElse(getRelativeImportance()));
        setTag(Stream.concat(getTag().stream(), other.getTag().stream()).distinct().collect(Collectors.toList()));
        setTodoList(Stream.concat(getTodoList().stream(), other.getTodoList().stream()).distinct().collect(Collectors.toList()));
    }

    public AccountNoticeInfo setAccountOperation(AccountNotice accountOperation) {
        if (accountOperation == null) {
            return this;
        }
        setAid(accountOperation.getAid());
        setId(accountOperation.getNid());
        setLooked(accountOperation.getLooked());
        setTop(accountOperation.getTop());
        setDel(accountOperation.getDel());
        setRelativeImportance(accountOperation.getRelativeImportance());
        return this;
    }

    public String getAid() {
        return aid;
    }

    public AccountNoticeInfo setAid(String aid) {
        this.aid = aid;
        return this;
    }


    public Boolean getLooked() {
        return looked;
    }

    public AccountNoticeInfo setLooked(Boolean read) {
        looked = read;
        return this;
    }

    public Boolean getTop() {
        return top;
    }

    public AccountNoticeInfo setTop(Boolean top) {
        this.top = top;
        return this;
    }

    public Boolean getDel() {
        return del;
    }

    public AccountNoticeInfo setDel(Boolean delete) {
        del = delete;
        return this;
    }

    public Integer getRelativeImportance() {
        return relativeImportance;
    }

    public AccountNoticeInfo setRelativeImportance(Integer relativeImportance) {
        this.relativeImportance = relativeImportance;
        return this;
    }

}
