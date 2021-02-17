<template>
  <div class="result">
    <div class="title">满足下列任意条件可收到该通知:</div>
    <div class="list">
      <div
        :class="['item', 'organization', d.dynamic ? 'dynamic' : '']"
        v-for="d in publish.organizationList"
        :key="d.oid"
      >
        <div class="item-oper">
          <a-tooltip class="del" title="删除" @click="removeOrganization(d.oid)"
            ><a-icon type="close-circle"
          /></a-tooltip>
        </div>
        <div class="item-dynamic">{{ d.dynamic ? "动态" : "静态" }}</div>
        <div class="item-name">{{ d.name }}</div>
        <div class="item-info">
          <div v-show="d.belong">加入</div>
          <div v-show="d.subscribe">关注</div>
        </div>
        <div class="item-account">
          <span v-show="organizationAccount[d.oid]">
            ({{ (organizationAccount[d.oid] || []).length }} 人匹配)
          </span>
        </div>
      </div>
      <div
        :class="['item', 'todo', d.dynamic ? 'dynamic' : '']"
        v-for="d in publish.todoList"
        :key="d.tid"
      >
        <div class="item-oper">
          <a-tooltip class="del" title="删除" @click="removeTodo(d.tid)"
            ><a-icon type="close-circle"
          /></a-tooltip>
        </div>
        <div class="item-dynamic">{{ d.dynamic ? "动态" : "静态" }}</div>
        <div class="item-name">{{ d.name }}</div>
        <div class="item-info">
          <div v-show="d.finish">完成</div>
          <div v-show="!d.finish">未完成</div>
        </div>
        <div class="item-account">
          <span v-show="todoAccount[d.tid]">
            ({{ (todoAccount[d.tid] || []).length }} 人匹配)
          </span>
        </div>
      </div>
      <div
        :class="['item', 'info', d.dynamic ? 'dynamic' : '']"
        v-for="d in publish.infoList"
        :key="d.iid"
      >
        <div class="item-oper">
          <a-tooltip class="del" title="删除" @click="removeInfo(d.iid)"
            ><a-icon type="close-circle"
          /></a-tooltip>
        </div>
        <div class="item-dynamic">{{ d.dynamic ? "动态" : "静态" }}</div>
        <div class="item-name">{{ d.name }}</div>
        <div class="item-info"></div>
        <div class="item-account">
          <span v-show="infoAccount[d.iid]">
            ({{ (infoAccount[d.iid] || []).length }} 人匹配)
          </span>
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
.result {
  .list {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    align-content: flex-start;
    .item {
      width: 270px;
      height: 180px;
      margin: 5px;
      border: 2px #888 solid;
      border-radius: 5px;
      display: flex;
      flex-direction: column;
      cursor: default;
      .item-dynamic {
        flex: 1;
        margin: 0 auto;
        font-size: 12px;
      }
      .item-name {
        flex: 5;
        margin: 0 auto;
        font-size: 28px;
      }
      .item-info {
        flex: 3;
        margin: 0 auto;
        font-size: 18px;
      }
      .item-account {
        flex: 2;
        margin: 0 auto;
        font-size: 16px;
      }
      .item-oper {
        height: 0;
        display: flex;
        justify-content: flex-end;
        margin-right: 5px;
        margin-top: 5px;
        .del {
          cursor: pointer;
          &:hover {
            color: #888;
          }
        }
      }
    }
    .dynamic {
      border-style: dashed;
    }
    .organization {
      background: #ff84;
      &:hover {
        background: #ff88;
      }
    }
    .todo {
      background: #f8f4;
      &:hover {
        background: #f8f8;
      }
    }
    .info {
      background: #8ff4;
      &:hover {
        background: #8ff8;
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
}
</style>
