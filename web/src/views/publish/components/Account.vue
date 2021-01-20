<template>
  <div>
    <div class="account box">
      <div class="input">
        <a-tooltip
          class="dynamic item"
          title="动态: 成员满足条件后自动收到通知, 静态: 仅发布时满足条件收到通知"
        >
          <a-switch
            v-model="dynamic"
            checkedChildren="动态"
            unCheckedChildren="静态"
          />
        </a-tooltip>
        <a-select v-model="select" class="select item">
          <a-select-option value="organization">组织</a-select-option>
          <a-select-option value="todo">待办</a-select-option>
          <a-select-option value="info">信息</a-select-option>
        </a-select>
        <a-select
          v-model="value"
          class="value item"
          v-show="select === 'organization'"
        >
          <a-select-option v-for="o in ol" :key="o.oid">
            {{ o.organizationName }}
          </a-select-option>
        </a-select>
        <a-switch
          class="item"
          v-show="select === 'organization'"
          v-model="subscribe"
          checkedChildren="关注"
          unCheckedChildren="关注"
        />
        <a-switch
          class="item"
          v-show="select === 'organization'"
          v-model="belong"
          checkedChildren="属于"
          unCheckedChildren="属于"
        />
        <a-select v-model="value" class="value item" v-show="select === 'todo'">
          <a-select-option v-for="t in todos" :key="t.id">
            {{ t.title }}
          </a-select-option>
        </a-select>
        <a-switch
          class="item"
          v-show="select === 'todo'"
          v-model="finish"
          checkedChildren="完成"
          unCheckedChildren="未完成"
        />
        <a-button class="item" type="primary" size="small" @click="searchAdd"
          ><a-icon type="search" />查询</a-button
        >
        <a-button class="item" type="primary" size="small" @click="add"
          ><a-icon type="plus" />添加</a-button
        >
      </div>
      <div class="res">
        <a-tooltip
          class="account"
          v-for="a in result"
          :key="a.id"
          :title="a.id"
          >{{ a.name }}</a-tooltip
        >
      </div>
    </div>
    <div class="show">
      <a-divider></a-divider>
      满足下列任意条件可收到该通知:
      <div class="added">
        <div
          class="organization item"
          v-for="o in publish.organizationList"
          :key="o.oid"
        >
          <div class="text">
            <span v-if="o.dynamic">任何时候</span>
            <span v-else>当前</span>
            <span v-if="o.belong">加入</span>
            <span v-if="o.belong && o.subscribe">或</span>
            <span v-if="o.subscribe">关注</span>
            <span>{{ o.name }}</span
            >组织.<span v-show="organizationAccount[o.oid]"
              >({{ organizationAccount[o.oid].length }}条匹配)</span
            >
          </div>
          <a-tooltip title="移除" class="oper">
            <a-icon type="minus-circle" @click="removeOrganization(o.oid)"
          /></a-tooltip>
          <a-tooltip title="展开" class="oper">
            <a-icon type="plus-circle" @click="flatOrganization(o)"
          /></a-tooltip>
        </div>
        <div class="todo item" v-for="t in publish.todoList" :key="t.tid">
          <div class="text">
            <span v-if="t.dynamic">任何时候</span>
            <span v-else>当前</span>
            <span v-if="!t.finish">未</span>完成<span>{{ t.name }}</span
            >事项.<span v-show="todoAccount[t.tid]"
              >({{ todoAccount[t.tid].length }}条匹配)</span
            >
          </div>
          <a-tooltip title="移除" class="oper">
            <a-icon type="minus-circle" @click="removeTodo(t.tid)"
          /></a-tooltip>
          <a-tooltip title="展开" class="oper">
            <a-icon type="plus-circle" @click="flatTodo(t)"
          /></a-tooltip>
        </div>
        <div class="info item" v-for="i in publish.infoList" :key="i.iid">
          <div class="text">
            <span v-if="i.dynamic">任何时候</span>
            <span v-else>当前</span>
            <span>{{ i.name }}</span>
          </div>
          <a-tooltip title="移除" class="oper">
            <a-icon type="minus-circle" @click="removeInfo(i.iid)"
          /></a-tooltip>
          <a-tooltip title="展开" class="oper">
            <a-icon type="plus-circle" @click="flatInfo(i)"
          /></a-tooltip>
        </div>
      </div>
    </div>
    <div class="operation">
      <a-button type="primary" @click="search" class="button">
        <a-icon type="search" />查询</a-button
      >
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "Account",
  data() {
    return {
      dynamic: false,
      select: "organization",
      value: "",
      belong: true,
      subscribe: true,
      finish: true,
      text: "",
      todos: [],
      result: [],
      organizationAccount: {},
      todoAccount: {},
      infoAccount: {}
    };
  },
  computed: {
    ...mapState({
      publish: state => state.publish,
      user: state => state.user,
      token: state => state.token
    }),
    ol() {
      return (this.user.organizations || []).filter(o => o.role === 0);
    }
  },
  methods: {
    add() {
      let { dynamic, value, belong, subscribe, finish, text } = { ...this };
      if (value === "") {
        this.$notification["error"]({
          message: "内容为空"
        });
        return;
      }
      if (this.select === "organization") {
        let o = this.user.organizations.filter(o => o.oid === value)[0];
        if (
          !this.addOrganization({
            dynamic,
            oid: o.oid,
            name: o.organizationName,
            belong,
            subscribe
          })
        ) {
          return;
        }
      } else if (this.select === "todo") {
        let t = this.todos.filter(t => t.id === value)[0];
        if (
          !this.addTodo({
            dynamic,
            tid: t.id,
            name: t.title,
            finish
          })
        ) {
          return;
        }
      } else if (this.select === "info") {
        this.publish.infoList.push({
          dynamic,
          iid: value,
          text: text
        });
      }
      this.dynamic = false;
      this.select = "organization";
      this.value = "";
      this.belong = true;
      this.subscribe = true;
      this.finish = true;
      this.text = "";
    },
    searchAdd() {
      let { dynamic, value, belong, subscribe, finish, text } = { ...this };
      if (value === "") {
        this.$notification["error"]({
          message: "内容为空"
        });
        return;
      }
      if (this.select === "organization") {
        let o = this.user.organizations.filter(o => o.oid === value)[0];
        this.searchOrganization(
          {
            dynamic,
            oid: o.oid,
            name: o.organizationName,
            belong,
            subscribe
          },
          () => (this.result = this.organizationAccount[value] || [])
        );
      } else if (this.select === "todo") {
        let t = this.todos.filter(t => t.id === value)[0];
        this.searchTodo(
          {
            dynamic,
            tid: t.id,
            name: t.title,
            finish
          },
          () => (this.result = this.todoAccount[value] || [])
        );
      } else if (this.select === "info") {
        this.searchInfo({
          dynamic,
          iid: value,
          text: text
        });
      }
    },
    addOrganization(o) {
      if (!o.belong && !o.subscribe) {
        this.$notification["error"]({
          message: "条件错误"
        });
        return false;
      }
      if (this.publish.organizationList.filter(o1 => o1.oid === o.oid).length) {
        this.$notification["error"]({
          message: "该条件已存在",
          description: "如需修改，请先删除"
        });
        return false;
      }
      this.publish.organizationList.push(o);
      return true;
    },
    addTodo(todo) {
      if (this.publish.todoList.filter(t => t.id === todo.tid).length) {
        this.$notification["error"]({
          message: "该条件已存在",
          description: "如需修改，请先删除"
        });
        return false;
      }
      this.publish.todoList.push(todo);
      return true;
    },
    removeOrganization(oid) {
      this.publish.organizationList = this.publish.organizationList.filter(
        o => o.oid !== oid
      );
    },
    removeTodo(tid) {
      this.publish.todoList = this.publish.todoList.filter(t => t.tid !== tid);
    },
    removeInfo(iid) {
      this.publish.infoList = this.publish.infoList.filter(i => i.iid !== iid);
    },
    searchOrganization(o, f) {
      Axios.post(`notice/notice/publish/getPublishOrganization`, o).then(
        res => {
          if (res.data.success) {
            this.organizationAccount[o.oid] = res.data.data.accounts;
            f();
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    },
    searchTodo(t, f) {
      Axios.post(`notice/notice/publish/getPublishTodo`, t).then(res => {
        if (res.data.success) {
          this.todoAccount[t.tid] = res.data.data.accounts;
          f();
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    searchInfo(i, f) {
      Axios.post(`notice/notice/publish/getPublishInfo`, i).then(res => {
        if (res.data.success) {
          this.infoAccount[i.iid] = res.data.data.accounts;
          f();
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    flatOrganization(o) {
      if (!this.organizationAccount[o.oid]) {
        this.searchOrganization(o);
      }
    },
    flatTodo(t) {
      if (!this.todoAccount[t.tid]) {
        this.searchTodo(t);
      }
    },
    flatInfo(i) {
      if (!this.infoAccount[i.iid]) {
        this.searchInfo(i);
      }
    },
    search() {
      Axios.post(
        `/notice/notice/publish/getPublicNoticeAccount`,
        this.publish
      ).then(res => {
        if (res.data.success) {
          res.data.data.forEach(d => {
            if (d.type === "PublishOrganization") {
              this.organizationAccount[d.source.oid] = d.accounts;
            } else if (d.type === "PublishTodo") {
              this.todoAccount[d.source.tid] = d.accounts;
            } else if (d.type === "PublishInfo") {
              this.infoAccount[d.source.iid] = d.accounts;
            }
          });
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    getTodos() {
      Axios.get(`/notice/notice/publish/getAllTodo?token=${this.token}`).then(
        res => {
          if (res.data.success) {
            this.todos = res.data.data;
          } else {
            this.$notification["error"]({
              message: res.data.code,
              description: res.data.message
            });
          }
        }
      );
    }
  },
  watch: {
    select() {
      this.result = [];
    }
  },
  mounted() {
    this.getTodos();
  }
};
</script>

<style lang="less" scoped>
.box {
  .input {
    display: flex;
    margin: 5px 0;
    .item {
      margin: auto 5px;
    }
    .value {
      flex: auto;
    }
  }
  .res {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    .account {
      margin: 5px;
    }
  }
}
.show {
  .added {
    .item {
      margin: 5px 10px;
      display: flex;
      border-bottom: 2px #0000 solid;
      cursor: default;
      .text {
        margin: auto;
        margin-left: 0;
      }
      .oper {
        margin: auto;
        margin-right: 25px;
        margin-left: 0;
      }
      &:hover {
        border-bottom: 2px #888 solid;
      }
    }
  }
}
.operation {
  display: flex;
  .button {
    margin: 0 10px;
    &:first-of-type {
      margin-left: auto;
    }
  }
}
</style>
