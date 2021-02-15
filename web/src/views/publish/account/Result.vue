<template>
  <div class="result">
    <div class="show">
      <a-divider></a-divider>
      满足下列任意条件可收到该通知:
      <div class="added">
        <!-- organization -->
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
              >({{ (organizationAccount[o.oid] || []).length }}条匹配)</span
            >
          </div>
          <a-tooltip title="移除" class="oper">
            <a-icon type="minus-circle" @click="removeOrganization(o.oid)"
          /></a-tooltip>
          <a-tooltip title="展开" class="oper">
            <a-icon type="plus-circle" @click="flatOrganization(o)"
          /></a-tooltip>
        </div>
        <!-- todo -->
        <div class="todo item" v-for="t in publish.todoList" :key="t.tid">
          <div class="text">
            <span v-if="t.dynamic">任何时候</span>
            <span v-else>当前</span>
            <span v-if="!t.finish">未</span>完成<span>{{ t.name }}</span
            >事项.<span v-show="todoAccount[t.tid]"
              >({{ (todoAccount[t.tid] || []).length }}条匹配)</span
            >
          </div>
          <a-tooltip title="移除" class="oper">
            <a-icon type="minus-circle" @click="removeTodo(t.tid)"
          /></a-tooltip>
          <a-tooltip title="展开" class="oper">
            <a-icon type="plus-circle" @click="flatTodo(t)"
          /></a-tooltip>
        </div>
        <!-- info -->
        <div class="info item" v-for="i in publish.infoList" :key="i.iid">
          <div class="text">
            <span v-if="i.dynamic">任何时候</span>
            <span v-else>当前</span>
            <span>{{ i.name }}</span
            >为<span>{{ i.text }}</span
            >.<span v-show="infoAccount[i.iid]"
              >({{ (infoAccount[i.iid] || []).length }}条匹配)</span
            >
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
export default {
  name: "Result",
  data() {
    return {
      organizationAccount: {},
      todoAccount: {},
      infoAccount: {}
    };
  },
  computed: {
    ...mapState({
      publish: state => state.publish
    })
  },
  methods: {
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
    searchOrganization(data, f) {
      this.request({
        method: "post",
        url: `/notice/publish/getPublishOrganization`,
        data
      }).then(publish => {
        this.organizationAccount[data.oid] = publish.accounts;
        f();
      });
    },
    searchTodo(data, f) {
      this.request({
        method: "post",
        url: `/notice/publish/getPublishTodo`,
        data
      }).then(publish => {
        this.todoAccount[data.tid] = publish.accounts;
        f();
      });
    },
    searchInfo(data, f) {
      this.request({
        method: "post",
        url: `/notice/publish/getPublishInfo`,
        data
      }).then(publish => {
        this.infoAccount[data.iid] = publish.accounts;
        f();
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
      this.request({
        method: "post",
        url: `/notice/publish/getPublicNoticeAccount`,
        data: this.publish
      }).then(publish => {
        publish.forEach(d => {
          if (d.type === "PublishOrganization") {
            this.organizationAccount[d.source.oid] = d.accounts;
          } else if (d.type === "PublishTodo") {
            this.todoAccount[d.source.tid] = d.accounts;
          } else if (d.type === "PublishInfo") {
            this.infoAccount[d.source.iid] = d.accounts;
          }
        });
        this.$forceUpdate();
      });
    }
  }
};
</script>

<style lang="less" scoped>
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
