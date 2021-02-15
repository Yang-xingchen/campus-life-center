<template>
  <div>
    <Setting @show="showtype = $event" @divider="divider = $event" />
    <Account
      class="publish"
      v-if="analysis.nid"
      :showtype="showtype"
      :divider="divider"
      :accounts="publish_account"
      :percent="(100 * analysis.accountNotice.length) / pubilishList.length"
    >
      <template v-slot:title>
        发布情况:
        {{ analysis.accountNotice.length }} / {{ pubilishList.length }}
        <a-tooltip
          title="发现未收到通知用户, 这可能是因为有新的满足条件的用户而该条件并非动态"
          :class="
            analysis.accountNotice.length < pubilishList.length ? 'err' : ''
          "
          ><a-icon
            type="exclamation-circle"
            v-if="analysis.accountNotice.length < pubilishList.length"
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
      let pl = [...this.analysis.publishAccountsList];
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
    publish_account() {
      return this.pubilishList.filter(
        a => this.publish[this.getAccountInfo(a.id) !== null]
      );
    },
    looked_account() {
      return this.analysis.accountNotice.filter(a => this.ao_r[a.looked]);
    },
    top_account() {
      return this.analysis.accountNotice.filter(a => this.ao_t[a.top]);
    },
    del_account() {
      return this.analysis.accountNotice.filter(a => this.ao_d[a.del]);
    }
  },
  data() {
    return {
      analysis: { accountNotice: [], publishAccountsList: [], accountTodos: [] },
      showtype: "id",
      divider: ",",
      publish: { true: false, false: true },
      ao_r: { true: false, false: true },
      ao_t: { true: false, false: false },
      ao_d: { true: true, false: false }
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
    getAnalysis() {
      if (!this.notice.id) {
        return;
      }
      this.request({
        method: "get",
        url: `/notice/${this.notice.id}/accountAnalysis?token=${this.token}`
      }).then(analysis => {
        this.analysis = analysis;
        this.analysis.accountNotice = this.analysis.accountNotice.map(a => {
          return {
            ...a,
            ...this.pubilishList.filter(p => p.id === a.aid)[0]
          };
        });
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
