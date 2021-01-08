package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class PublishNotice implements Serializable {

    @ApiModelProperty
    private String token;
    @ApiModelProperty("通知")
    private Notice notice;
    @ApiModelProperty("todo")
    private AddTodoRequest todo;
    @ApiModelProperty("收集信息列表")
    private List<List<InformationConditions>> infoCollectList;
    @ApiModelProperty("静态成员")
    private List<String> accountList;
    @ApiModelProperty("按钮成员列表")
    private List<PublishTodo> todoList;
    @ApiModelProperty("信息成员列表")
    private List<PublishInfo> infoList;
    @ApiModelProperty("组织成员列表")
    private List<PublishOrganization> organizationList;

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

    public AddTodoRequest getTodo() {
        return todo;
    }

    public PublishNotice setTodo(AddTodoRequest todo) {
        this.todo = todo;
        return this;
    }

    public List<List<InformationConditions>> getInfoCollectList() {
        return infoCollectList;
    }

    public PublishNotice setInfoCollectList(List<List<InformationConditions>> infoCollectList) {
        this.infoCollectList = infoCollectList;
        return this;
    }

    public List<String> getAccountList() {
        return accountList;
    }

    public PublishNotice setAccountList(List<String> accountList) {
        this.accountList = accountList;
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
}
