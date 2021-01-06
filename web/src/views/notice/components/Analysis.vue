<template>
  <div>
    {{ analysis }}
    <a-divider />
    <div class="publish">
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
      <span>展开<a-icon type="down"/></span>
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
    pubilishList() {
      let pl = [...this.analysis.publishAccountList];
      let l = [];
      for (let k of pl) {
        l.push(...k.accounts);
      }
      return [...new Set(l.map(v => v.id))];
    }
  },
  data() {
    return {
      analysis: {}
    };
  },
  methods: {
    getAnalysis() {
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
  color: red;
}
.publish_open {
  display: flex;
  span {
    margin: 0 0 0 auto;
    cursor: pointer;
  }
}
</style>
