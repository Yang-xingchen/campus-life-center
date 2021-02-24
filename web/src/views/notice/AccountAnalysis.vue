<template>
  <div>
    <Setting @show="showtype = $event" @divider="divider = $event" />
    <a-button type="primary" @click="rePublish" v-if="operTotal < total"
      >重新发布</a-button
    >
    <Account
      class="publish"
      v-if="analysis.nid"
      :showtype="showtype"
      :divider="divider"
      :accounts="publish_account"
      :percent="(100 * operTotal) / total"
    >
      <template v-slot:title>
        发布情况:
        {{ operTotal }} / {{ total }}
        <a-tooltip
          title="发现未收到通知用户, 这可能是因为有新的满足条件的用户而该条件并非动态"
          :class="operTotal < total ? 'err' : ''"
          ><a-icon type="exclamation-circle" v-if="operTotal < total"
        /></a-tooltip>
      </template>
      <template v-slot:swith>
        已收到用户<a-switch
          v-model="publish[true]"
          checked-children="显示"
          un-checked-children="不显示"/>
        未收到用户<a-switch
          v-model="publish[false]"
          checked-children="显示"
          un-checked-children="不显示"
      /></template>
    </Account>
    <a-divider><span :class="['divider', theme]">用户状态</span></a-divider>
    <div class="operation">
      <Account
        :showtype="showtype"
        :divider="divider"
        :accounts="looked_account"
        :percent="lookedPer"
      >
        <template v-slot:title>读取比例:</template>
        <template v-slot:swith>
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
        </template>
      </Account>
      <Account
        :showtype="showtype"
        :divider="divider"
        :accounts="top_account"
        :percent="topPer"
      >
        <template v-slot:title>置顶比例:</template>
        <template v-slot:swith>
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
        </template>
      </Account>
      <Account
        :showtype="showtype"
        :divider="divider"
        :accounts="del_account"
        :percent="delPer"
      >
        <template v-slot:title>完成比例:</template>
        <template v-slot:swith>
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
        </template>
      </Account>
    </div>
  </div>
</template>

<script>
import Setting from "./components/AnalysisSetting";
import Account from "./components/AnalysisAccount";
import { mapState } from "vuex";
export default {
  name: "analysis",
  components: { Setting, Account },
  computed: {
    ...mapState({
      token: state => state.token,
      notice: state => state.notice,
      theme: state => state.theme
    }),
    lookedPer() {
      return (
        (100 * Object.values(this.accountOper).filter(a => a.looked).length) /
        this.total
      );
    },
    topPer() {
      return (
        (100 * Object.values(this.accountOper).filter(a => a.top).length) /
        this.total
      );
    },
    delPer() {
      return (
        (100 * Object.values(this.accountOper).filter(a => a.del).length) /
        this.total
      );
    },
    accounts() {
      if (!this.analysis) {
        return {};
      }
      let pl = this.analysis.publishAccountsList || [];
      let accounts = {};
      for (let type of pl) {
        for (let account of type.accounts) {
          accounts[account.id] = account.name;
        }
      }
      return accounts;
    },
    total() {
      return Object.keys(this.accounts).length;
    },
    accountOper() {
      if (!this.analysis) {
        return {};
      }
      return Object.fromEntries(
        this.analysis.accountNotice.map(a => [a.aid, { ...a }])
      );
    },
    operTotal() {
      return Object.keys(this.accountOper).length;
    },
    publish_account() {
      let l = [];
      for (let a in this.accounts) {
        if (this.publish[this.accountOper[a] != undefined]) {
          l.push({ id: a, name: this.accounts[a] });
        }
      }
      return l;
    },
    looked_account() {
      return Object.values(this.accountOper)
        .filter(a => this.ao_r[a.looked])
        .map(a => {
          return { id: a.aid, name: this.accounts[a.aid] };
        });
    },
    top_account() {
      return Object.values(this.accountOper)
        .filter(a => this.ao_t[a.top])
        .map(a => {
          return { id: a.aid, name: this.accounts[a.aid] };
        });
    },
    del_account() {
      return Object.values(this.accountOper)
        .filter(a => this.ao_d[a.del])
        .map(a => {
          return { id: a.aid, name: this.accounts[a.aid] };
        });
    }
  },
  data() {
    return {
      analysis: {
        accountNotice: [],
        publishAccountsList: [],
        accountTodos: []
      },
      showtype: "id",
      divider: ",",
      publish: { true: false, false: true },
      show_full: false,
      ao_r: { true: false, false: true },
      ao_t: { true: false, false: false },
      ao_d: { true: true, false: false }
    };
  },
  methods: {
    getAnalysis() {
      if (!this.notice.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/notice/${this.notice.id}/accountAnalysis?token=${this.token}`
      }).then(analysis => (this.analysis = analysis));
    },
    rePublish() {
      if (!this.notice.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/notice/publish/${this.notice.id}/republish?token=${this.token}`
      }).then(success => {
        if (success) {
          this.getAnalysis();
        }
      });
    }
  },
  created() {
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
@import "../../assets/theme.less";
.err {
  color: #ff8888;
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
