<template>
  <div>
    <a-divider orientation="right">
      <span
        :class="['divider', 'setting_title', theme]"
        v-show="setting"
        @click="setting = !setting"
        >设置<a-icon type="up"
      /></span>
      <span
        :class="['divider', 'setting_title', theme]"
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
      <a-divider />
    </div>
    <!-- -- -- -- -->
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
      <span class="show_switch" v-show="publish_show">
        已收到用户<a-switch
          v-model="publish[true]"
          checked-children="显示"
          un-checked-children="不显示"
        />
        未收到用户<a-switch
          v-model="publish[false]"
          checked-children="显示"
          un-checked-children="不显示"
        />
      </span>
      <a-progress
        :showInfo="false"
        :percent="(100 * analysis.accountNotice.length) / pubilishList.length"
      ></a-progress>
      <div class="publish_box box">
        <span
          class="open"
          @click="publish_show = !publish_show"
          v-show="!publish_show"
          >显示用户列表<a-icon type="down"
        /></span>
        <span
          class="open"
          @click="publish_show = !publish_show"
          v-show="publish_show"
          >关闭用户列表<a-icon type="up"
        /></span>
        <div class="publish_list account_content" v-show="publish_show">
          {{ publish_show_account }}
        </div>
      </div>
    </div>
    <a-divider><span :class="['divider', theme]">用户状态</span></a-divider>
    <!-- -- -- -- -->
    <div class="operation">
      读取比例:
      <span class="show_switch" v-if="account_show">
        已读
        <a-switch
          v-model="ao_r[true]"
          checked-children="显示"
          un-checked-children="不显示"
        />
        未读
        <a-switch
          v-model="ao_r[false]"
          checked-children="显示"
          un-checked-children="不显示"
        />
      </span>
      <a-progress :percent="lookedPer" />
      置顶比例:
      <span class="show_switch" v-if="account_show">
        置顶
        <a-switch
          v-model="ao_t[true]"
          checked-children="显示"
          un-checked-children="不显示"
        />
        未置顶
        <a-switch
          v-model="ao_t[false]"
          checked-children="显示"
          un-checked-children="不显示"
        />
      </span>
      <a-progress :percent="topPer" />
      完成比例:
      <span class="show_switch" v-if="account_show">
        完成
        <a-switch
          v-model="ao_d[true]"
          checked-children="显示"
          un-checked-children="不显示"
        />
        未完成
        <a-switch
          v-model="ao_d[false]"
          checked-children="显示"
          un-checked-children="不显示"
        />
      </span>
      <a-progress :percent="delPer" />
      <div class="account_box box">
        <span
          class="open"
          @click="account_show = !account_show"
          v-show="!account_show"
          >显示用户列表<a-icon type="down"
        /></span>
        <span
          class="open"
          @click="account_show = !account_show"
          v-show="account_show"
          >关闭用户列表<a-icon type="up"
        /></span>
        <div class="accountOper account_content" v-show="account_show">
          {{ operation_show_account }}
        </div>
      </div>
    </div>
    <a-divider v-if="analysis.accountTodos && analysis.accountTodos.length > 0"
      ><span :class="['divider', theme]">待办完成情况</span></a-divider
    >
    <!-- -- -- -- -->
    <div class="todo" v-if="hasTodo">
      <div v-for="t in analysis.accountTodos" :key="t.id">
        {{ t.value }}:
        <span class="show_switch" v-if="todo_show">
          完成<a-switch
            v-model="todo[t.id][true]"
            checked-children="显示"
            un-checked-children="不显示"
          />
          未完成<a-switch
            v-model="todo[t.id][false]"
            checked-children="显示"
            un-checked-children="不显示"
          />
        </span>
        <a-progress :percent="todoFinPer(t.id)"></a-progress>
      </div>
      <div class="todo_box box">
        <span class="open" @click="todo_show = !todo_show" v-show="!todo_show"
          >显示用户列表<a-icon type="down"
        /></span>
        <span class="open" @click="todo_show = !todo_show" v-show="todo_show"
          >关闭用户列表<a-icon type="up"
        /></span>
        <div
          class="todo_list account_content"
          v-show="todo_show"
          v-if="analysis.accountTodos && analysis.accountTodos.length > 0"
        >
          {{ todo_show_account }}
        </div>
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
      notice: state => state.notice,
      theme: state => state.theme
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
        .filter(a => this.publish[this.getAccountInfo(a.id) !== null])
        .map(a => a[this.showtype])
        .join(this.divider);
    },
    operation_show_account() {
      return this.analysis.accountNotice
        .map(a => {
          return { ...a };
        })
        .filter(
          a => this.ao_r[a.looked] || this.ao_t[a.top] || this.ao_d[a.del]
        )
        .map(
          a => this.pubilishList.filter(p => p.id === a.aid)[0][this.showtype]
        )
        .join(this.divider);
    },
    todo_show_account() {
      if (
        !this.analysis.accountTodos ||
        this.analysis.accountTodos.length === 0
      ) {
        return "";
      }
      let list = [];
      for (let k of this.analysis.accountTodos) {
        if (this.todo[k.id][k.finish]) {
          list.push(k.aid);
        }
      }
      list = [...new Set(list)];
      return list
        .map(a => this.pubilishList.filter(p => p.id === a)[0][this.showtype])
        .join(this.divider);
    },
    hasTodo() {
      return (
        this.analysis.accountTodos && this.analysis.accountTodos.length > 0
      );
    }
  },
  data() {
    return {
      analysis: { accountNotice: [], publishAccountList: [], accountTodos: [] },
      setting: true,
      showtype: "id",
      divider: ",",
      publish_show: false,
      publish: { true: false, false: true },
      account_show: false,
      ao_r: { true: false, false: false },
      ao_t: { true: false, false: false },
      ao_d: { true: false, false: false },
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
      Axios.get(`/notice/${this.notice.id}/analysis?token=${this.token}`).then(
        res => {
          if (res.data.success) {
            this.analysis = res.data.data;
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
  mounted() {
    this.getAnalysis();
  },
  watch: {
    notice() {
      this.getAnalysis();
    },
    analysis() {
      if (!this.analysis || !this.analysis.accountTodos) {
        return;
      }
      this.todo = Object.fromEntries(
        [...this.analysis.accountTodos].map(t => [
          t.id,
          { true: false, false: false }
        ])
      );
    }
  }
};
</script>

<style lang="less" scoped>
@import "../../../assets/theme.less";
.setting_title {
  cursor: pointer;
}
.err {
  color: #ff8888;
}
.publish {
  .setting {
    font-size: 16px;
  }
}
.open {
  cursor: pointer;
  display: block;
  text-align: right;
}
.show_switch {
  float: right;
  margin-right: 30px;
}
.account_content {
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  word-break: keep-all;
  padding-left: 30px;
}
.divider {
  background: #0000;
  &.dark {
    color: @d-fg;
  }
  &.light {
    color: @l-fg;
  }
}
</style>
