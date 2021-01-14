package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublishNotice implements Serializable {

    @ApiModelProperty
    private String token;
    @ApiModelProperty("通知")
    private Notice notice;
    @ApiModelProperty("发布id")
    private String pid;
    @ApiModelProperty("标签")
    private List<String> tag;
    @ApiModelProperty("todo")
    private AddTodoRequest todo;
    @ApiModelProperty("收集信息列表")
    private List<PublishInfoCollect> publishInfoCollectList;
    @ApiModelProperty("静态成员")
    private List<String> accountList;
    @ApiModelProperty("按钮成员列表")
    private List<PublishTodo> todoList;
    @ApiModelProperty("信息成员列表")
    private List<PublishInfo> infoList;
    @ApiModelProperty("组织成员列表")
    private List<PublishOrganization> organizationList;

    public static class PublishInfoCollect extends AddInfoRequest {
        private String name;

        public String getName() {
            return name;
        }

        public PublishInfoCollect setName(String name) {
            this.name = name;
            return this;
        }
    }

    public String getPid() {
        return pid;
    }

    public PublishNotice setPid(String pid) {
        this.pid = pid;
        return this;
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

    public List<String> getTag() {
        return tag == null ? new ArrayList<>() : tag;
    }

    public PublishNotice setTag(List<String> tag) {
        this.tag = tag;
        return this;
    }

    public AddTodoRequest getTodo() {
        return todo;
    }

    public PublishNotice setTodo(AddTodoRequest todo) {
        this.todo = todo;
        return this;
    }

    public List<PublishInfoCollect> getInfoCollectList() {
        return publishInfoCollectList == null ? new ArrayList<>() : publishInfoCollectList;
    }

    public PublishNotice setInfoCollectList(List<PublishInfoCollect> publishInfoCollectList) {
        this.publishInfoCollectList = publishInfoCollectList;
        return this;
    }

    public List<String> getAccountList() {
        return accountList == null ? new ArrayList<>() : accountList;
    }

    public PublishNotice setAccountList(List<String> accountList) {
        this.accountList = accountList;
        return this;
    }

    public List<PublishTodo> getTodoList() {
        return todoList == null ? new ArrayList<>() : todoList;
    }

    public PublishNotice setTodoList(List<PublishTodo> todoList) {
        this.todoList = todoList;
        return this;
    }

    public List<PublishInfo> getInfoList() {
        return infoList == null ? new ArrayList<>() : infoList;
    }

    public PublishNotice setInfoList(List<PublishInfo> infoList) {
        this.infoList = infoList;
        return this;
    }

    public List<PublishOrganization> getOrganizationList() {
        return organizationList == null ? new ArrayList<>() : organizationList;
    }

    public PublishNotice setOrganizationList(List<PublishOrganization> organizationList) {
        this.organizationList = organizationList;
        return this;
    }
}
