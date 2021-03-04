<template>
  <div class="result">
    <div class="title">满足下列任意条件可收到该通知:</div>
    <div class="list">
      <div
        :class="[
          'item',
          ['', 'organization', 'todo', 'info'][d.cType],
          d.dynamic ? 'dynamic' : ''
        ]"
        v-for="d in publish.publishConditions"
        :key="d.oid"
      >
        <div class="item-oper">
          <a-tooltip class="del" title="删除" @click="remove(d)"
            ><a-icon type="close-circle"
          /></a-tooltip>
        </div>
        <div class="item-dynamic">{{ d.dynamic ? "动态" : "静态" }}</div>
        <div class="item-name">{{ d.name }}</div>
        <div class="item-info" v-show="d.cType === 1">
          <div v-show="d.belong">加入</div>
          <div v-show="d.subscribe">关注</div>
        </div>
        <div class="item-info" v-show="d.cType === 2">
          <div v-show="d.finish">完成</div>
          <div v-show="!d.finish">未完成</div>
        </div>
        <div class="item-info" v-show="d.cType === 3">
          <div>{{ infoCondition[d.type] }}</div>
          <div>{{ d.text }}</div>
        </div>
        <div class="item-account">
          <span v-show="accounts[d.ref]">
            ({{ (accounts[d.ref] || []).length }} 人匹配)
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
  name: "Result",
  data() {
    return {
      accounts: {},
      infoCondition
    };
  },
  computed: {
    ...mapState({
      publish: state => state.publish
    })
  },
  methods: {
    remove(data) {
      this.publish.publishConditions = this.publish.publishConditions.filter(
        d => d.ref !== data.ref
      );
    },
    searchOrganization(data) {
      this.request(
        {
          method: "get",
          url: `/notice/organization/condition/${data.ref}/accounts`,
          data
        },
        null,
        res => res.data
      ).then(aids => {
        this.accounts[data.ref] = aids;
        this.$forceUpdate();
      });
    },
    searchTodo(data) {
      this.request(
        {
          method: "get",
          url: `/todo/condition/${data.ref}/accounts`,
          data
        },
        null,
        res => res.data
      ).then(aids => {
        this.accounts[data.ref] = aids;
        this.$forceUpdate();
      });
    },
    searchInfo(data) {
      this.request(
        {
          method: "get",
          url: `/info/condition/${data.ref}/accounts`,
          data
        },
        null,
        res => res.data
      ).then(aids => {
        this.accounts[data.ref] = aids;
        this.$forceUpdate();
      });
    },
    search() {
      this.publish.publishConditions.forEach(data => {
        if (data.cType === 1) {
          this.searchOrganization(data);
        } else if (data.cType === 2) {
          this.searchTodo(data);
        } else if (data.cType === 3) {
          this.searchInfo(data);
        }
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
