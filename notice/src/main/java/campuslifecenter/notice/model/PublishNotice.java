package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.DynamicOrganizationObserve;
import campuslifecenter.notice.entry.DynamicTodoObserve;
import campuslifecenter.notice.entry.Notice;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PublishNotice implements Serializable {

    @ApiModelProperty
    private String token;
    @ApiModelProperty("通知")
    private Notice notice;
    @ApiModelProperty("按钮")
    private List<PublishTodo> todoList;
    @ApiModelProperty("信息")
    private List<PublishInfo> infoList;
    @ApiModelProperty("组织")
    private List<PublishOrganization> organizationList;
    @ApiModelProperty("成员")
    private List<String> accountList;

    public static class PublishTodo implements Serializable {
        @ApiModelProperty("按钮id")
        private int id;
        @ApiModelProperty("完成情况")
        private boolean finish;
        @ApiModelProperty("通知类型是否为动态")
        private boolean dynamic;

        public DynamicTodoObserve toEntry(long nid) {
            DynamicTodoObserve observe = new DynamicTodoObserve();
            observe.setTid(getId());
            observe.setNid(nid);
            observe.setIsFinish(isFinish());
            return observe;
        }

        public int getId() {
            return id;
        }

        public PublishTodo setId(int id) {
            this.id = id;
            return this;
        }

        public boolean isFinish() {
            return finish;
        }

        public PublishTodo setFinish(boolean finish) {
            this.finish = finish;
            return this;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public PublishTodo setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
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
            PublishTodo that = (PublishTodo) o;
            return id == that.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "PublishTodo{" +
                    "id=" + id +
                    ", finish=" + finish +
                    '}';
        }
    }

    public static class PublishInfo implements Serializable {
        @ApiModelProperty("通知类型是否为动态")
        private boolean dynamic;

        public boolean isDynamic() {
            return dynamic;
        }

        public PublishInfo setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
            return this;
        }
    }

    public static class PublishOrganization implements Serializable {
        @ApiModelProperty("组织id")
        private Integer id;
        @ApiModelProperty("是否属于该组织")
        private boolean belong;
        @ApiModelProperty("是否关注该组织")
        private boolean subscribe;
        @ApiModelProperty("通知类型是否为动态")
        private boolean dynamic;

        public DynamicOrganizationObserve toEntry(long nid) {
            DynamicOrganizationObserve observe = new DynamicOrganizationObserve();
            observe.setNid(nid);
            observe.setOid(getId());
            observe.setIsBelong(isBelong());
            observe.setIsSubscribe(isSubscribe());
            return observe;
        }

        public Integer getId() {
            return id;
        }

        public PublishOrganization setId(Integer id) {
            this.id = id;
            return this;
        }

        public boolean isBelong() {
            return belong;
        }

        public PublishOrganization setBelong(boolean belong) {
            this.belong = belong;
            return this;
        }

        public boolean isSubscribe() {
            return subscribe;
        }

        public PublishOrganization setSubscribe(boolean subscribe) {
            this.subscribe = subscribe;
            return this;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public PublishOrganization setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
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
            PublishOrganization that = (PublishOrganization) o;
            return belong == that.belong &&
                    subscribe == that.subscribe &&
                    Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, belong, subscribe);
        }

        @Override
        public String toString() {
            return "PublishOrganization{" +
                    "id=" + id +
                    ", belong=" + belong +
                    ", subscribe=" + subscribe +
                    '}';
        }
    }

    public String getToken() {
        return token;
    }

    public PublishNotice setToken(String token) {
        this.token = token;
        return this;
    }

    public Notice getNotice() {
        return notice;
    }

    public PublishNotice setNotice(Notice notice) {
        this.notice = notice;
        return this;
    }

    public List<PublishTodo> getTodoList() {
        return todoList;
    }

    public PublishNotice setTodoList(List<PublishTodo> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<PublishInfo> getInfoList() {
        return infoList;
    }

    public PublishNotice setInfoList(List<PublishInfo> infoList) {
        this.infoList = infoList;
        return this;
    }

    public List<PublishOrganization> getOrganizationList() {
        return organizationList;
    }

    public PublishNotice setOrganizationList(List<PublishOrganization> organizationList) {
        this.organizationList = organizationList;
        return this;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public PublishNotice setAccountList(List<String> accountList) {
        this.accountList = accountList;
        return this;
    }
}
