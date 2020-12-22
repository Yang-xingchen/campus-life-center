package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.Notice;
import io.swagger.annotations.ApiModelProperty;

public class PublicNotice {

    @ApiModelProperty
    private String token;
    @ApiModelProperty("通知")
    private Notice notice;
    @ApiModelProperty("通知成员类型是否为动态")
    private boolean dynamic;

    public String getToken() {
        return token;
    }

    public PublicNotice setToken(String token) {
        this.token = token;
        return this;
    }

    public Notice getNotice() {
        return notice;
    }

    public PublicNotice setNotice(Notice notice) {
        this.notice = notice;
        return this;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public PublicNotice setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
        return this;
    }

}
