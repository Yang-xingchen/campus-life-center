<template>
  <div>
    <a-divider orientation="right">
      <span
        class="divider setting_title"
        v-show="setting"
        @click="setting = !setting"
        >设置<a-icon type="up"
      /></span>
      <span
        class="divider setting_title"
        v-show="!setting"
        @click="setting = !setting"
        >设置<a-icon type="down"
      /></span>
    </a-divider>
    <div class="setting" v-show="setting">
      显示方式:
      <a-radio-group v-model="showtype">
        <a-radio-button value="id">id</a-radio-button>
        <a-radio-button value="name">名称</a-radio-button>
      </a-radio-group>
      分隔符:
      <a-radio-group v-model="divider">
        <a-radio-button value=",">英文逗号</a-radio-button>
        <a-radio-button value=" ">空格</a-radio-button>
      </a-radio-group>
    </div>
    <a-divider />
    <div class="publish" v-if="analysis.nid">
      发布情况:
      {{ analysis.accountNotice.length }} / {{ pubilishList.length }}
      <a-tooltip
        title="发现未收到通知用户, 这可能是因为有新的满足条件的用户而该条件并非动态"
        :class="
          analysis.accountNotice.length < pubilishList.length ? 'err' : ''
        "
      >
        <a-icon
          type="exclamation-circle"
          v-if="analysis.accountNotice.length < pubilishList.length"
      /></a-tooltip>
    </div>
    <a-progress
      :showInfo="false"
      :percent="(100 * analysis.accountNotice.length) / pubilishList.length"
    ></a-progress>
    <div class="publish_open">
      <span
        class="open"
        @click="publish_show = !publish_show"
        v-show="!publish_show"
        >展开<a-icon type="down"
      /></span>
      <span
        class="open"
        @click="publish_show = !publish_show"
        v-show="publish_show"
        >收起<a-icon type="up"
      /></span>
      <div class="publish_list" v-show="publish_show">
        显示收到通知用户<a-switch v-model="publish_success" />
        显示未收到通知用户<a-switch v-model="publish_fail" />
        <div class="publish_content">{{ publish_show_account }}</div>
      </div>
    </div>
    <a-divider><span class="divider">用户状态</span></a-divider>
    <div class="accountOperation">
      读取比例:
      <span v-if="account_show">显示已读 <a-switch v-model="ao_r"/></span>
      <span v-if="account_show">显示未读 <a-switch v-model="ao_nr"/></span>
      <a-progress :percent="lookedPer" />
      置顶比例:
      <span v-if="account_show">显示置顶 <a-switch v-model="ao_t"/></span>
      <span v-if="account_show">显示未置顶 <a-switch v-model="ao_nt"/></span>
      <a-progress :percent="topPer" />
      完成比例:
      <span v-if="account_show">显示完成 <a-switch v-model="ao_d"/></span>
      <span v-if="account_show">显示未完成 <a-switch v-model="ao_nd"/></span>
      <a-progress :percent="delPer" />
      <div class="account_box">
        <span
          class="open"
          @click="account_show = !account_show"
          v-show="!account_show"
          >展开<a-icon type="down"
        /></span>
        <span
          class="open"
          @click="account_show = !account_show"
          v-show="account_show"
          >收起<a-icon type="up"
        /></span>
        <div class="accountOper" v-show="account_show">
          {{ operation_show_account }}
        </div>
      </div>
    </div>
    <a-divider v-if="analysis.accountTodos && analysis.accountTodos.length > 0"
      ><span class="divider">待办完成情况</span></a-divider
    >
    <div v-if="analysis.accountTodos && analysis.accountTodos.length > 0">
      <div class="todo" v-for="t in analysis.accountTodos" :key="t.id">
        {{ t.value }}:
        <span v-if="todo_show">显示完成<a-switch v-model="todo[t.id].f"/></span>
        <span v-if="todo_show"
          >显示未完成<a-switch v-model="todo[t.id].n"
        /></span>
        <a-progress :percent="todoFinPer(t.id)"></a-progress>
      </div>
    </div>
    <div
      class="todo_box"
      v-if="analysis.accountTodos && analysis.accountTodos.length > 0"
    >
      <span class="open" @click="todo_show = !todo_show" v-show="!todo_show"
        >展开<a-icon type="down"
      /></span>
      <span class="open" @click="todo_show = !todo_show" v-show="todo_show"
        >收起<a-icon type="up"
      /></span>
      <div
        class="todo_list"
        v-show="todo_show"
        v-if="analysis.accountTodos && analysis.accountTodos.length > 0"
      >
        {{ account_todo_show }}
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import Axios from "axios";
export default {
  name: "analysis",
  computed: {
    ...mapState({
      token: state => state.token,
      notice: state => state.notice
    }),
    lookedPer() {
      return (
        (100 * this.analysis.accountNotice.filter(a => a.looked).length) /
        this.analysis.accountNotice.length
      );
    },
    topPer() {
      return (
        (100 * this.analysis.accountNotice.filter(a => a.top).length) /
        this.analysis.accountNotice.length
      );
    },
    delPer() {
      return (
        (100 * this.analysis.accountNotice.filter(a => a.del).length) /
        this.analysis.accountNotice.length
      );
    },
    pubilishList() {
      let pl = [...this.analysis.publishAccountList];
      let l = [];
      for (let k of pl) {
        l.push(...k.accounts);
      }
      let obj = {};
      return l.reduce((c, n) => {
        obj[n.id] ? "" : (obj[n.id] = true && c.push(n));
        return c;
      }, []);
    },
    publish_show_account() {
      return this.pubilishList
        .filter(
          a =>
            (this.publish_success && this.getAccountInfo(a.id) !== null) ||
            (this.publish_fail && this.getAccountInfo(a.id) === null)
        )
        .map(a => a[this.showtype])
        .join(this.divider);
    },
    operation_show_account() {
      return this.analysis.accountNotice
        .map(a => {
          return { ...a };
        })
        .filter(
          a =>
            (this.ao_r && a.looked) ||
            (this.ao_nr && !a.looked) ||
            (this.ao_t && a.top) ||
            (this.ao_nt && !a.top) ||
            (this.ao_d && a.del) ||
            (this.ao_nd && !a.del)
        )
        .map(
          a => this.pubilishList.filter(p => p.id === a.aid)[0][this.showtype]
        )
        .join(this.divider);
    },
    account_todo_show() {
      if (this.analysis.accountTodos && this.analysis.accountTodos.length > 0) {
        return "";
      }
      let list = [];
      for (let k of this.analysis.accountTodos) {
        if (
          (this.todo[k.id].f && k.finish) ||
          (this.todo[k.id].n && !k.finish)
        ) {
          list.push(k.aid);
        }
      }
      list = [...new Set(list)];
      return list
        .map(a => this.pubilishList.filter(p => p.id === a)[0][this.showtype])
        .join(this.divider);
    }
  },
  data() {
    return {
      analysis: { accountNotice: [], publishAccountList: [], accountTodos: [] },
      setting: true,
      showtype: "id",
      divider: ",",
      publish_show: false,
      publish_success: false,
      publish_fail: true,
      account_show: false,
      ao_r: false,
      ao_nr: false,
      ao_t: false,
      ao_nt: false,
      ao_d: false,
      ao_nd: false,
      todo_show: false,
      todo: {}
    };
  },
  methods: {
    getAccountInfo(aid) {
      let arr = this.analysis.accountNotice.filter(n => n.aid === aid);
      if (arr.length > 0) {
        return arr[0];
      } else {
        return null;
      }
    },
    todoFinPer(tid) {
      return (
        (100 *
          this.analysis.accountTodos.filter(t => t.finish && t.id === tid)
            .length) /
        this.analysis.accountTodos.filter(t => t.id === tid).length
      );
    },
    getAnalysis() {
      if (!this.notice.id) {
        return;
      }
      Axios.get(
        `notice/notice/${this.notice.id}/analysis?token=${this.token}`
      ).then(res => {
        if (res.data.success) {
          this.analysis = res.data.data;
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
    this.getAnalysis();
  },
  watch: {
    notice() {
      this.getAnalysis();
    },
    analysis() {
      this.todo = Object.fromEntries(
        [...this.analysis.accountTodos].map(t => [t.id, { f: false, n: false }])
      );
    }
  }
};
</script>

<style lang="less" scoped>
.setting_title {
  cursor: pointer;
}
.err {
  color: #ff8888;
}
.setting {
  font-size: 16px;
}
.open {
  cursor: pointer;
  float: right;
}
.publish_open {
  min-height: 30px;
  .publish_list {
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    .publish_content {
      width: 100%;
      word-break: keep-all;
      padding-left: 30px;
    }
  }
}
.accountOper,
.todo_list {
  padding-top: 30px;
  padding-left: 30px;
}
.account_box {
  min-height: 30px;
}
.todo_box {
  min-height: 30px;
}
.divider {
  color: #fff;
}
</style>
