<template>
  <div>
    {{ analysis }}
    <a-divider><span class="divider">设置</span></a-divider>
    <div class="setting">
      显示方式:
      <a-radio-group v-model="showtype">
        <a-radio-button value="id">id</a-radio-button>
        <a-radio-button value="name">名称</a-radio-button>
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
        // todo
      </div>
    </div>
    <a-divider><span class="divider">用户状态</span></a-divider>
    <div class="accountOperation">
      读取比例:
      <a-progress :percent="lookedPer" />
      置顶比例:
      <a-progress :percent="topPer" />
      完成比例:
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
          // todo
        </div>
      </div>
    </div>
    <a-divider><span class="divider">待办状态</span></a-divider>
    <div class="todo" v-for="t in analysis.accountTodos" :key="t.id">
      {{ t.value }}:
      <a-progress :percent="todoFinPer(t.id)"></a-progress>
    </div>
    <div class="todo_box">
      <span class="open" @click="todo_show = !todo_show" v-show="!todo_show"
        >展开<a-icon type="down"
      /></span>
      <span class="open" @click="todo_show = !todo_show" v-show="todo_show"
        >收起<a-icon type="up"
      /></span>
      <div class="todo_list" v-show="todo_show">
        // todo
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
    }
  },
  data() {
    return {
      analysis: { accountNotice: [], publishAccountList: [] },
      showtype: "id",
      publish_show: false,
      account_show: false,
      todo_show: false
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
    }
  }
};
</script>

<style lang="less" scoped>
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
    .account {
      margin: 3px 5px;
      cursor: pointer;
    }
  }
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
