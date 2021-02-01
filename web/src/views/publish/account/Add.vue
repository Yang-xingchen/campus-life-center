<template>
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
      <a-select @change="result = []" v-model="select" class="select item">
        <a-select-option value="organization">组织</a-select-option>
        <a-select-option value="todo">待办</a-select-option>
        <a-select-option value="info">信息</a-select-option>
      </a-select>
      <div class="organization content" v-show="select === 'organization'">
        <a-select
          @change="result = []"
          v-model="organization"
          class="value item"
        >
          <a-select-option v-for="o in ol" :key="o.oid">
            {{ o.organizationName }}
          </a-select-option>
        </a-select>
        <a-switch
          @change="result = []"
          class="item"
          v-model="subscribe"
          checkedChildren="关注"
          unCheckedChildren="关注"
        />
        <a-switch
          @change="result = []"
          class="item"
          v-model="belong"
          checkedChildren="属于"
          unCheckedChildren="属于"
        />
      </div>
      <div class="todo content" v-show="select === 'todo'">
        <a-select @change="result = []" v-model="todo" class="value item">
          <a-select-option v-for="t in todos" :key="t.id">
            {{ t.title }}
          </a-select-option>
        </a-select>
        <a-switch
          @change="result = []"
          class="item"
          v-show="select === 'todo'"
          v-model="finish"
          checkedChildren="完成"
          unCheckedChildren="未完成"
        />
      </div>
      <div class="info content" v-show="select === 'info'">
        <a-select
          @change="result = []"
          v-model="info"
          class="value item select"
        >
          <a-select-option v-for="i in infos" :key="i.id">
            <a-tooltip :title="i.name" placement="topLeft">{{
              i.name
            }}</a-tooltip>
          </a-select-option>
        </a-select>
        <a-input v-model="text" class="value item" placeholder="值" />
      </div>
      <a-button class="item" type="primary" size="small" @click="search"
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
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "Add",
  data() {
    return {
      dynamic: false,
      select: "organization",
      organization: "",
      todo: "",
      info: "",
      text: "",
      belong: true,
      subscribe: true,
      finish: true,
      todos: [],
      infos: [],
      result: []
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
    },
    mape() {
      const organization = this.publish.organizationList;
      const todo = this.publish.todoList;
      const info = this.publish.infoList;
      return {
        get: {
          organization: this.getOrganization,
          todo: this.getTodo,
          info: this.getInfo
        },
        checkExist: {
          organization: o => organization.filter(o1 => o1.oid === o.oid).length,
          todo: t => todo.filter(t1 => t1.tid === t.tid).length,
          info: i => info.filter(i1 => i1.iid === i.iid).length
        },
        list: { organization, todo, info },
        search: {
          organization: this.searchOrganization,
          todo: this.searchTodo,
          info: this.searchInfo
        }
      };
    }
  },
  methods: {
    getOrganization() {
      let { dynamic, organization, belong, subscribe } = { ...this };
      if (organization === "") {
        this.$notification["error"]({
          message: "内容为空"
        });
        return null;
      }
      if (!belong && !subscribe) {
        this.$notification["error"]({
          message: "条件错误"
        });
        return false;
      }
      let o = this.user.organizations.filter(o => o.oid === organization)[0];
      return {
        dynamic,
        oid: o.oid,
        name: o.organizationName,
        belong,
        subscribe
      };
    },
    getTodo() {
      let { dynamic, todo, finish } = { ...this };
      if (todo === "") {
        this.$notification["error"]({
          message: "内容为空"
        });
        return null;
      }
      let t = this.todos.filter(t => t.id === todo)[0];
      return {
        dynamic,
        tid: todo,
        name: t.title,
        finish
      };
    },
    getInfo() {
      let { dynamic, info, text } = { ...this };
      if (info === "") {
        this.$notification["error"]({
          message: "内容为空"
        });
        return null;
      }
      let i = this.infos.filter(i => i.id === info)[0];
      return {
        dynamic,
        iid: info,
        name: i.name,
        text: text
      };
    },
    add() {
      const res = this.mape.get[this.select]();
      if (!res) {
        return;
      }
      if (this.mape.checkExist[this.select](res)) {
        this.$notification["error"]({
          message: "该条件已存在",
          description: "如需修改，请先删除"
        });
        return false;
      }
      this.mape.list[this.select].push(res);
      this.dynamic = false;
      this.select = "organization";
      this.value = "";
      this.belong = true;
      this.subscribe = true;
      this.finish = true;
      this.text = "";
    },
    search() {
      const res = this.mape.get[this.select]();
      if (!res) {
        return;
      }
      this.mape.search[this.select](res);
    },
    searchOrganization(o) {
      Axios.post(`/notice/publish/getPublishOrganization`, o).then(res => {
        if (res.data.success) {
          this.result = res.data.data.accounts;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    searchTodo(t) {
      Axios.post(`/notice/publish/getPublishTodo`, t).then(res => {
        if (res.data.success) {
          this.result = res.data.data.accounts;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    searchInfo(i) {
      Axios.post(`/notice/publish/getPublishInfo`, i).then(res => {
        if (res.data.success) {
          this.result = res.data.data.accounts;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    getTodos() {
      Axios.get(`/notice/publish/getAllTodo?token=${this.token}`).then(res => {
        if (res.data.success) {
          this.todos = res.data.data;
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    },
    getInfos() {
      Axios.get(`/info/getExistInfo`).then(res => {
        if (res.data.success) {
          this.infos = res.data.data.filter(i => i.type != 1);
        } else {
          this.$notification["error"]({
            message: res.data.code,
            description: res.data.message
          });
        }
      });
    }
  },
  mounted() {
    this.getTodos();
    this.getInfos();
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
    .content {
      display: flex;
      flex: auto;
      .value {
        flex: auto;
      }
      .select {
        min-width: 100px;
      }
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
</style>
