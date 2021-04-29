<template>
  <div>
    <div class="notice">
      <Screen id="screen" :notices="notices" @update-screen="updateScreen" />
      <NoticeMain
        id="notice_main"
        :notices="show_notice"
        :total="total"
        :loaded="notices.length"
      />
    </div>
  </div>
</template>

<script>
import Screen from "../views/noticeCards/Screen";
import NoticeMain from "../views/noticeCards/NoticeMain";
import { mapState } from "vuex";

export default {
  name: "NoticeCards",
  components: { Screen, NoticeMain },
  data() {
    return {
      total: ~(1 << 31),
      page: -1,
      notices: [],
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState({
      token: state => state.token,
      subscribe: state => state.subscribes,
      belong: state => Object.values(state.user.organizations).map(o => o.id)
    }),
    show_notice() {
      return this.notices.filter(this.filterFuntion);
    },
    complete() {
      return this.notices.length >= this.total;
    }
  },
  created() {
    this.load();
  },
  methods: {
    updateScreen(f) {
      this.filterFuntion = f;
    },
    load() {
      if (this.total == -1 || this.total > this.notices.length) {
        this.page++;
        this.request({
          method: "get",
          url: `/notice/getAll?token=${this.token}&page=${this.page}`
        }).then(notices => {
          this.total = notices.total;
          notices.items
            .map(n => {
              n.accountImportance = n.importance + n.relativeImportance;
              n.showTag = [
                ...n.tag,
                n.top ? "置顶" : "",
                !n.looked ? "未读" : "",
                n.del ? "已删除" : "",
                `重要度: ${n.accountImportance}星(${
                  n.relativeImportance > 0 ? "+" : ""
                }${n.relativeImportance})`,
                n.todoList.filter(t => !t.finish).length > 0
                  ? "待办: 未完成"
                  : "",
                "发布者: " + n.creatorName,
                n.organizationName ? "发布组织: " + n.organizationName : "",
                "类型: " + ["消息", "事件", "活动"][n.publicType]
              ].filter(t => t !== "");
              n.score = this.getScore(n);
              return n;
            })
            .forEach(n => this.notices.push(n));
          setTimeout(this.load, (500 * notices.items.length) / this.total);
        });
      }
    },
    getScore(item) {
      let importance = item.importance + item.relativeImportance;
      importance = Math.min(importance, 5);
      importance = Math.max(importance, 1);
      let score = importance;
      score |= item.top ? 0x40 : 0;
      score |= item.looked ? 0 : 0x20;
      score |= [0, ...(this.belong || [])].filter(
        oid => oid === item.organization
      ).length
        ? 0x10
        : 0;
      score |= [0, ...(this.subscribe || [])].filter(
        oid => oid === item.organization
      ).length
        ? 0x08
        : 0;
      score -= item.del ? 0x7f : 0;
      return score;
    }
  }
};
</script>

<style lang="less" scope>
.notice {
  width: 1500px;
  margin: 0 auto;
  #screen {
    width: 365px;
    margin: 5px;
    margin-top: 25px;
    float: left;
    position: fixed;
    background: #8882;
    border: 2px #8888 solid;
  }
  #notice_main {
    width: 1115px;
    margin: 5px;
    float: right;
  }
}
</style>
