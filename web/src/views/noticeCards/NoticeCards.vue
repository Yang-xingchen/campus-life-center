<template>
  <div>
    <div class="notice">
      <Screen
        id="screen"
        :class="theme"
        :notices="notices"
        @update-screen="updateScreen"
      />
      <NoticeMain id="notice_main" :notices="show_notice" />
    </div>
  </div>
</template>

<script>
import Screen from "./components/Screen";
import NoticeMain from "./components/NoticeMain";
import Axios from "axios";
import { mapState } from "vuex";

export default {
  name: "NoticeCards",
  components: { Screen, NoticeMain },
  data() {
    return {
      notices: [],
      filterFuntion: n => n !== null
    };
  },
  computed: {
    ...mapState(["token", "theme"]),
    show_notice() {
      return this.notices
        .map(n => {
          n.accountImportance = n.importance + n.relativeImportance;
          n.showTag = [
            ...n.tag,
            n.top ? "置顶" : "",
            !n.looked ? "未读" : "",
            n.del ? "已删除" : "",
            "重要度: " +
              n.accountImportance +
              "星(" +
              (n.relativeImportance > 0
                ? "+" + n.relativeImportance
                : n.relativeImportance) +
              ")",
            n.todoList.filter(t => !t.finish).length > 0 ? "待办: 未完成" : "",
            "发布者: " + n.creatorName,
            "发布组织: " + n.organizationName,
            "类型: " + ["消息", "事件", "活动"][n.publicType]
          ].filter(t => t !== "");
          return n;
        })
        .filter(this.filterFuntion);
    }
  },
  mounted() {
    Axios.get(`notice/notice/getAll?token=${this.token}`).then(res => {
      if (res.data.success) {
        this.notices = res.data.data;
      } else {
        this.$notification["error"]({
          message: res.data.code,
          description: res.data.message
        });
      }
    });
  },
  methods: {
    updateScreen(f) {
      this.filterFuntion = f;
    }
  }
};
</script>

<style lang="less" scope>
@import "../../assets/theme.less";
.notice {
  width: 1500px;
  margin: 0 auto;
  #screen {
    width: 365px;
    margin: 5px;
    margin-top: 25px;
    float: left;
    position: fixed;
    &.dark {
      background: @d-bg2;
    }
    &.light {
      background: @l-bg2;
    }
  }
  #notice_main {
    width: 1115px;
    margin: 5px;
    float: right;
  }
}
</style>
