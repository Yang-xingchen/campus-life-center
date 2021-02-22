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
          <a-select-option v-for="o in ol" :key="o.id">
            {{ o.name }}
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
            {{ t.content }}
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
        <a-select
          @change="result = []"
          v-model="type"
          class="value item select"
        >
          <a-select-option v-for="(v, k) of infoCondition" :key="k">{{
            v
          }}</a-select-option>
        </a-select>
        <a-input v-model="text" class="value item" placeholder="值" />
      </div>
      <a-button class="item" type="primary" size="small" @click="search"
        ><a-icon type="search" disabled />查询</a-button
      >
      <a-button class="item" type="primary" size="small" @click="add"
        ><a-icon type="plus" />添加</a-button
      >
    </div>
    <div class="res">
      <div class="account" v-for="a in result" :key="a.id">
        <div class="id">{{ a.id }}</div>
        <div class="name">{{ a.name }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
const infoCondition = {
  0: "相等",
  1: "结尾",
  2: "开头",
  3: "包含",
  8: "不等",
  9: "结尾不为",
  10: "开头不为",
  11: "不包含",
  16: "数字相等",
  17: "大于",
  18: "小于",
  20: "之间(空格分割)",
  24: "数字不等",
  25: "不大于",
  26: "不小于"
};
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
      type: "0",
      todos: [],
      infos: [],
      result: [],
      infoCondition
    };
  },
  computed: {
    ...mapState({
      publish: state => state.publish,
      user: state => state.user,
      token: state => state.token
    }),
    ol() {
      return this.user.organizations || [];
    },
    mape() {
      return {
        get: {
          organization: this.getOrganization,
          todo: this.getTodo,
          info: this.getInfo
        },
        search: {
          organization: this.searchOrganization,
          todo: this.searchTodo,
          info: this.searchInfo
        },
        create: {
          organization: this.createOrganization,
          todo: this.createTodo,
          info: this.createInfo
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
      let o = this.user.organizations[organization];
      return {
        dynamic,
        oid: o.id,
        name: o.name,
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
        name: t.content,
        finish
      };
    },
    getInfo() {
      let { dynamic, info, text, type } = { ...this };
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
        type: +type,
        name: i.name,
        text
      };
    },
    add() {
      const res = this.mape.get[this.select]();
      if (!res) {
        return;
      }
      this.mape.create[this.select](res);
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
    searchOrganization(data) {
      this.request(
        {
          method: "post",
          url: `/notice/publish/getPublishOrganization`,
          data
        },
        null,
        res => res.data
      ).then(aids => (this.result = aids));
    },
    searchTodo(data) {
      this.request(
        {
          method: "post",
          url: `/notice/publish/getPublishTodo`,
          data
        },
        null,
        res => res.data
      ).then(aids => (this.result = aids));
    },
    searchInfo(data) {
      this.request(
        {
          method: "post",
          url: `/notice/publish/getPublishInfo`,
          data
        },
        null,
        res => res.data
      ).then(aids => (this.result = aids));
    },
    createOrganization(data) {
      this.request({
        method: "post",
        url: `/notice/organization/condition/create`,
        data
      }).then(ref =>
        this.publish.publishConditions.push({ ref, type: 1, ...data })
      );
    },
    createTodo(data) {
      this.request({
        method: "post",
        url: `/todo/condition/create`,
        data
      }).then(ref =>
        this.publish.publishConditions.push({ ref, type: 2, ...data })
      );
    },
    createInfo(data) {
      this.request({
        method: "post",
        url: `/info/condition/create`,
        data
      }).then(ref =>
        this.publish.publishConditions.push({ ref, type: 3, ...data })
      );
    },
    getTodos() {
      this.request({
        method: "get",
        url: `/notice/publish/getAllTodo?token=${this.token}`
      }).then(todos => (this.todos = todos));
    },
    getInfos() {
      this.request({
        method: "get",
        url: `/info/getExistInfo`
      }).then(infos => (this.infos = infos.filter(i => i.type != 1)));
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
    margin-top: 20px;
    max-height: 140px;
    overflow-y: auto;
    .account {
      margin: 5px;
      width: 150px;
      height: 70px;
      background: #8884;
      border-radius: 5px;
      text-align: center;
      cursor: default;
      &:hover {
        background: #8888;
      }
      .id {
        font-size: 16px;
        height: 20px;
        color: #888;
      }
      .name {
        font-size: 22px;
        height: 50px;
        line-height: 50px;
      }
    }
  }
}
</style>
